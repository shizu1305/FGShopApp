package it.hueic.kenhoang.fgshopapp.connect;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectAPI extends AsyncTask<String, Void, String> {
    String url;
    List<HashMap<String,String>> attrs;
    StringBuilder data;
    boolean isMethod;
    //Get
    public ConnectAPI(String url) {
        this.url = url;
        isMethod = true;
    }
    //Post
    public ConnectAPI(String url, List<HashMap<String,String>> attrs) {
        this.url = url;
        this.attrs = attrs;
        isMethod = false;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL urlParse = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlParse.openConnection();
            if (isMethod)
                methodGet(httpURLConnection);
            else
                methodPost(httpURLConnection);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data.toString();
    }

    private String methodGet(HttpURLConnection httpURLConnection) {
        String dataGet = "";
        InputStream inputStream = null;
        try {
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);

            data = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                data.append(line);
            }

            dataGet = data.toString();
            bufferedReader.close();
            reader.close();
            inputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataGet;
    }

    private String methodPost(HttpURLConnection httpURLConnection) {
        String dataPost = "";
        try {
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder();

            int count = attrs.size();

            for (int i = 0; i < count; i++) {
                String key = "";
                String value = "";
                for (Map.Entry<String, String> values : attrs.get(i).entrySet()) {
                    key = values.getKey();
                    value = values.getValue();
                }
                builder.appendQueryParameter(key, value);
            }

            String query = builder.build().getEncodedQuery();
            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter writer = new BufferedWriter(streamWriter);

            writer.write(query);
            writer.flush();
            writer.close();
            streamWriter.close();
            outputStream.close();

            dataPost = methodGet(httpURLConnection);

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataPost;
    }
}
