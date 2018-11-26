package com.example.absol.examensarbete;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class PopularNamePOJO implements Comparable<PopularNamePOJO> , Parcelable {

    private String name;
    private int rank;
    private int amount;

    public PopularNamePOJO(String name, int amount){
        this.name = name;
        this.amount = amount;
        this.rank = 0;
    }

    public int getAmount() {
        return amount;
    }

    public int getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public int compareTo(@NonNull PopularNamePOJO compareMe) {
        int compareAmount = compareMe.getAmount();

        return compareAmount-this.amount;
    }


    public PopularNamePOJO(Parcel parcel) {
        this.name = parcel.readString();
        this.amount = parcel.readInt();
        this.rank = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(amount);
        dest.writeInt(rank);
    }

    public static Creator<PopularNamePOJO> CREATOR = new Creator<PopularNamePOJO>() {

        @Override
        public PopularNamePOJO createFromParcel(Parcel source) {
            return new PopularNamePOJO(source);
        }
        @Override
        public PopularNamePOJO[] newArray(int size) {
            return new PopularNamePOJO[size];
        }
    };
}
