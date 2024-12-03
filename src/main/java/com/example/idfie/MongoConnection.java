package com.example.idfie;

import com.mongodb.client.*;
import org.bson.Document;

public class MongoConnection {
    public void connectToMongo(String id, String firstName, String fatherName, String email, String phone, String department, String campus, String batch) {
        User user = new User();
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("idgenerator");
            MongoCollection<Document> collection = database.getCollection("users");

            // Check if the ID_No already exists in the database
            Document query = new Document("ID_No", user.idNo);
            FindIterable<Document> result = collection.find(query);
            if (result.first() != null) {
                System.out.println("ID already exists in the database. Cannot create a new document.");
            } else {
                // ID does not exist, create a new document
                Document doc = new Document("ID_No", id)
                        .append("name", firstName)
                        .append("father_name", fatherName)
                        .append("email", email)
                        .append("phone_number", phone)
                        .append("department", department)
                        .append("campus", campus)
                        .append("batch", batch);

                collection.insertOne(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
