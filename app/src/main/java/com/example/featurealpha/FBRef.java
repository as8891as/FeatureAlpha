package com.example.featurealpha;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FBRef {
    public static FirebaseFirestore FBFS = FirebaseFirestore.getInstance();
    public static CollectionReference refJsonData = FBFS.collection("json_data");
}