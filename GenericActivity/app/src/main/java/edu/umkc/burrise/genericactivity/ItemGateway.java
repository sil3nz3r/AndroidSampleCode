package edu.umkc.burrise.genericactivity;

import java.util.ArrayList;

public class ItemGateway {
    private static ItemGateway ourInstance = null;

    private ArrayList<Item> items = null;

    public static ItemGateway getInstance()
    {
        if (ourInstance == null) {
            ourInstance = new ItemGateway();
            ourInstance.items = new ArrayList<Item>();
        }
        return ourInstance;
    }

    private ItemGateway() {
    }

    public void add(Item i) {
        items.add(i);
    }

    public String getImageFileName(String label) {
        for (Item i : items) {
            if (i.imageLabel.equals(label))
                return i.imageFileName;
        }
        // Probably better to throw an exception here, but I want
        //   to keep the example simple.
        return "<not found>";
    }
}
