package com.example.beerapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class SerialBitmap implements Serializable {

    private Bitmap bitmap;

    SerialBitmap(byte[] beerImageBytes) {
        // Take your existing call to BitmapFactory and put it here
        if (beerImageBytes != null)
            bitmap = BitmapFactory.decodeByteArray(beerImageBytes, 0, beerImageBytes.length);
        else
            bitmap = null;
    }

    // Converts the Bitmap into a byte array for serialization
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteStream);
        byte[] bitmapBytes = byteStream.toByteArray();
        out.write(bitmapBytes, 0, bitmapBytes.length);
    }

    // Deserializes a byte array representing the Bitmap and decodes it
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int b;
        while((b = in.read()) != -1)
            byteStream.write(b);
        byte[] bitmapBytes = byteStream.toByteArray();
        bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }

    Bitmap getBitmap() {
        return bitmap;
    }
}