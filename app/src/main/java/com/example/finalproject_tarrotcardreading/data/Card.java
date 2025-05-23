package com.example.finalproject_tarrotcardreading.data;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cards")
public class Card implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String number;
    private String name;
    private int imageResource;
    private String uprightMeaning;
    private String reversedMeaning;
    private String description;
    private boolean isReversed;

    public Card(String number, String name, int imageResource, String uprightMeaning, 
                String reversedMeaning, String description) {
        this.number = number != null ? number : "";
        this.name = name != null ? name : "";
        this.imageResource = imageResource;
        this.uprightMeaning = uprightMeaning != null ? uprightMeaning : "";
        this.reversedMeaning = reversedMeaning != null ? reversedMeaning : "";
        this.description = description != null ? description : "";
        this.isReversed = false;
    }

    // Parcelable实现
    protected Card(Parcel in) {
        id = in.readInt();
        number = in.readString();
        name = in.readString();
        imageResource = in.readInt();
        uprightMeaning = in.readString();
        reversedMeaning = in.readString();
        description = in.readString();
        isReversed = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(number);
        dest.writeString(name);
        dest.writeInt(imageResource);
        dest.writeString(uprightMeaning);
        dest.writeString(reversedMeaning);
        dest.writeString(description);
        dest.writeByte((byte) (isReversed ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getUprightMeaning() {
        return uprightMeaning;
    }

    public void setUprightMeaning(String uprightMeaning) {
        this.uprightMeaning = uprightMeaning;
    }

    public String getReversedMeaning() {
        return reversedMeaning;
    }

    public void setReversedMeaning(String reversedMeaning) {
        this.reversedMeaning = reversedMeaning;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isReversed() {
        return isReversed;
    }

    public void setReversed(boolean reversed) {
        isReversed = reversed;
    }

    // 兼容你所有用法
    public int getImageResId() {
        return imageResource;
    }
} 