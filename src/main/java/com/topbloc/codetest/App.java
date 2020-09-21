package com.topbloc.codetest;

import codetest.helper.StudentTranscript;
import codetest.helper.ParseData;

import java.io.IOException;
import java.util.*;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.HttpResponse;
import org.json.JSONObject;

public final class App {
    private App() {
    }

    public static void main(String[] args) {
        Map<Integer, StudentTranscript> students = new HashMap<>();
        ArrayList<Integer> femaleCSIDs = new ArrayList<>();
        int totalScore = 0;

        try {
            // Import and parse data
            ParseData.parseStudentInfo(students);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Iterate through populated student list to find requested data
        Set<Map.Entry<Integer, StudentTranscript>> studentSet = students.entrySet();
        for (Map.Entry<Integer, StudentTranscript> student : studentSet) {
            // Sum up final test scores
            totalScore += student.getValue().getFinalScore();
            // Look for female computer science majors
            if (student.getValue().getGender().contains("F")
                    && student.getValue().getMajor().contains("computer science")) {
                femaleCSIDs.add(student.getValue().getID());
            }
        }

        // Evaluate the class average, rounding to nearest integer
        int classAverage = Math.round(totalScore / students.size());

        String[] femaleCSMajors = new String[femaleCSIDs.size()];
        Collections.sort(femaleCSIDs);

        // Create a sorted string array of female CS majors' student IDs
        for (int i = 0; i < femaleCSIDs.size(); i++) {
            femaleCSMajors[i] = String.valueOf(femaleCSIDs.get(i));
        }

        try {
            // Set up for the JSON request
            String path = "http://54.90.99.192:5000/challenge";
            HttpClient cli = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(path);
    
            // Create the JSON according to prompt
            JSONObject json = new JSONObject();
            json.put("id", "felonda@wisc.edu");
            json.put("name", "Mo Felonda");
            json.put("average", classAverage);
            json.put("studentIds", femaleCSMajors);
    
            StringEntity entity = new StringEntity(json.toString());
            post.setEntity(entity);
            post.setHeader("Content-type", "application/json");
            post.setHeader("Accept", "application/json");
    
            // Execute JSON request
            HttpResponse response = cli.execute(post);
            System.out.print(response);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
