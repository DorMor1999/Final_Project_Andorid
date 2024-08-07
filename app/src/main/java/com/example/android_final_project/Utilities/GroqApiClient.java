package com.example.android_final_project.Utilities;

import com.example.android_final_project.Interfaces.ApiKeyGroq;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GroqApiClient{
    private final OkHttpClient client = new OkHttpClient();
    private final String apiKey = ApiKeyGroq.apiKey;

    public interface ChatCompletionCallback {
        void onResponse(String response);
        void onFailure(IOException e);
    }

    public void chatCompletion(String userMessage, JSONArray financialData, ChatCompletionCallback callback) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", "llama3-70b-8192");
            JSONArray messages = new JSONArray();
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", userMessage + "\n" + "Financial Data: " + financialData.toString());
            messages.put(message);
            jsonBody.put("messages", messages);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        RequestBody requestBody = RequestBody.create(jsonBody.toString(), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url("https://api.groq.com/openai/v1/chat/completions")
                .addHeader("Authorization", "Bearer " + apiKey)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    callback.onFailure(new IOException("Unexpected code " + response));
                    return;
                }

                try {
                    String responseBody = response.body().string();
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    String content = jsonResponse.getJSONArray("choices")
                            .getJSONObject(0)
                            .getJSONObject("message")
                            .getString("content");
                    callback.onResponse(content);
                } catch (JSONException e) {
                    callback.onFailure(new IOException("JSON parsing error", e));
                }
            }
        });
    }
}
