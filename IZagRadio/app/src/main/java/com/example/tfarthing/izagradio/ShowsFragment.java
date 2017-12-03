package com.example.tfarthing.izagradio;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by jtwheadon on 11/29/17.
 */

public class ShowsFragment extends Fragment {

    View myView;
    private List<Show> showList;
    private ListView showsListView;
    final static String SHOWS_PAGE_URL = "https://www.gonzaga.edu/Student-Development/Student-Involvement-and-Leadership/Events-and-Initiatives/iZAG/Our%20Shows.asp";
    final static String IMAGES_URL = "https://www.gonzaga.edu/Student-Development/Student-Involvement-and-Leadership/Events-and-Initiatives/iZAG%20Images/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Set Title
        getActivity().setTitle("Shows");
        //myView = inflater.inflate(R.layout.shows_layout, container, false);


        // Create GridLayout for the activity
        GridLayout gridLayout = new GridLayout(getActivity());
        gridLayout.setColumnCount(1);
        gridLayout.setPadding(20, 10, 20, 10);

        // Create text view for the header
        TextView header = new TextView(getActivity());
        header.setText("Our Shows");
        header.setTextSize(30);
        header.setTextColor(getResources().getColor(R.color.colorText));
        gridLayout.addView(header);

        // Create List View to display all the shows
        showsListView = new ListView(getActivity());
        // Set LayoutParams for the ListView
        GridLayout.LayoutParams showLayoutParams = new GridLayout.LayoutParams();
        showLayoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
        showLayoutParams.width = GridLayout.LayoutParams.MATCH_PARENT;
        showLayoutParams.topMargin = 15;
        showsListView.setLayoutParams(showLayoutParams);
        showsListView.setId(R.id.showListView);
        gridLayout.addView(showsListView);

        // Open up the show info if the user clicks on an item
        showsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Show selectedShow = (Show)adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getActivity(), ShowInfoActivity.class);
                intent.putExtra("showTitle", selectedShow.getTitle());
                intent.putExtra("showDescription", selectedShow.getDescription());
                intent.putExtra("showImageURL", selectedShow.getImageURL());
                startActivity(intent);
            }
        });

        // Create List for types of notes
        showList = new ArrayList<>();

        // Create the array adapter
        ArrayAdapter<Show> arrayAdapter = new ArrayAdapter<Show>(getActivity(), android.R.layout.activity_list_item, android.R.id.text1, showList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                // Current note type
                Show show = showList.get(position);

                TextView textView1 = (TextView)view.findViewById(android.R.id.text1);
                textView1.setText(show.getTitle());
                textView1.setTextSize(20);

                textView1.setTextColor(getResources().getColor(R.color.colorText));

                ImageView imageView = (ImageView)view.findViewById(android.R.id.icon);
                //imageView.setImageBitmap(show.getImage());
                Glide.with(getActivity()).load(show.getImageURL()).into(imageView);

                GridLayout.LayoutParams adapterViewParams = new GridLayout.LayoutParams();
                adapterViewParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
                adapterViewParams.width = GridLayout.LayoutParams.MATCH_PARENT;
                view.setLayoutParams(adapterViewParams);
                view.setPadding(0, 15, 0, 15);
                return view;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                // DropDownView will be same as normal View
                return getView(position, convertView, parent);
            }

            @Nullable
            @Override
            public Show getItem(int position) {
                return showList.get(position);
            }


        };

        showsListView.setAdapter(arrayAdapter);

        // Start the asynchronous task to parse the iZag web page and get content
        ParseWebPageTask asyncTask = new ParseWebPageTask();
        asyncTask.execute(SHOWS_PAGE_URL);
        //LoadImageTask loadImageTask = new LoadImageTask();
        //loadImageTask.execute(SHOWS_PAGE_URL);

        gridLayout.setBackgroundResource(R.drawable.main_background);
        return gridLayout;
    }

    private class ParseWebPageTask extends AsyncTask<String, Integer, List<Show>> {

        final String TAG = "ParseWebPageTask";
        @Override
        protected List<Show> doInBackground(String... strings) {
            // list of shows to be returned at end of task
            List<Show> shows = new ArrayList<>();

            // attempt to parse the webpage
            try {
                Document doc = Jsoup.connect(strings[0]).get();
                String title = doc.title();
                Element content = doc.getElementById("main_content");
                Elements tables = content.getElementsByTag("table");
                for (Element table : tables) {
                    Elements tableBodies = table.getElementsByTag("tbody");
                    for(Element tableBody : tableBodies) {
                        Elements tableRows = tableBody.getElementsByTag("tr");
                        for(Element tableRow: tableRows) {
                            Show show = new Show();
                           Elements tableDatas = tableRow.getElementsByTag("td");
                           String description = tableDatas.get(1).text();
                           if(description != null && !description.equals("")) {
                               show.setDescription(description);

                               // TODO: Implement better text processing, this is currently just a workaround
                               // Most of the descriptions begin with the title of the show
                               if (description.indexOf("is") != -1) {
                                   show.setTitle(description.substring(0, description.indexOf("is")));
                               } else if (description.indexOf(",") != -1) {
                                   show.setTitle(description.substring(0, description.indexOf(",")));
                               } else {
                                   show.setTitle("iZag Show");
                               }

                           }

                           // Get the image URL
                            Element imageContainer = tableDatas.get(0);
                            Elements links = imageContainer.getElementsByTag("a");
                            Elements images;
                            if (links == null || links.isEmpty()) {
                                images = imageContainer.getElementsByTag("img");
                            } else {
                                images = links.get(0).getElementsByTag("img");
                            }
                            if(images.isEmpty() || images == null)
                                continue;

                            // Format the image URL correctly
                            String htmlImageName = images.get(0).attr("src");
                            String nameExtension = htmlImageName.substring(htmlImageName.lastIndexOf("/") + 1);
                            String url = IMAGES_URL + nameExtension.replace(" ", "%20");
                            show.setImageURL(url);
                            shows.add(show);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return shows;
        }

        // onPostExecute() executes after doInBackground returns
        // return value of doInBackground is the parameter of onPostExecute()

        @Override
        protected void onPostExecute(List<Show> shows) {
            super.onPostExecute(shows);
            // Update the list of shows and notify adapter
            showList.addAll(shows);
            ArrayAdapter<Show> adapter = (ArrayAdapter<Show>)showsListView.getAdapter();
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
