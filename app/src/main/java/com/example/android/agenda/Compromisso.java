package com.example.android.agenda;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Compromisso implements Parcelable,Serializable {

    private String texto1;
    private String texto2;
    private String date;


    public  Compromisso(String texto1, String texto2,String date){

        this.texto1 = texto1;
        this.texto2 = texto2;
        this.date = date;

    }


    private Compromisso(Parcel in) {
        texto1 = in.readString();
        texto2 = in.readString();
        date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(texto1);
        dest.writeString(texto2);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Compromisso> CREATOR = new Creator<Compromisso>() {

        public Compromisso createFromParcel(Parcel in) {
            return new Compromisso(in);
        }

        @Override
        public Compromisso[] newArray(int size) {
            return new Compromisso[size];
        }
    };

    public String getTexto2() {
        return texto2;
    }

    public void setTexto2(String texto2) {
        this.texto2 = texto2;
    }

    public String getTexto1() {
        return texto1;
    }

    public void setTexto1(String texto1) {
        this.texto1 = texto1;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString() {
        return "Compromisso:" + texto1 + "\nDescricao: " + texto2 + "\nData: " + date;
    }
}

