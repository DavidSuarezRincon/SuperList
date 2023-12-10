package com.david.superlist.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;

public class TareaLista implements Parcelable {

    private String task;
    private String priority;
    private boolean isChecked;
    private int iconPriorityColor;

    public TareaLista(String task, String priority) {
        this.task = task;
        this.priority = priority;
        isChecked = false;
    }

    private TareaLista(Parcel in) {
        task = in.readString();
        priority = in.readString();
        iconPriorityColor = in.readInt();
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getPriority() {
        return priority;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getIconPriorityColor() {
        return iconPriorityColor;
    }

    public void setIconPriorityColor(int iconPriorityColor) {
        this.iconPriorityColor = iconPriorityColor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(task);
        dest.writeString(priority);
        dest.writeInt(iconPriorityColor);
    }

    public static final Parcelable.Creator<TareaLista> CREATOR = new Parcelable.Creator<TareaLista>() {
        @Override
        public TareaLista createFromParcel(Parcel in) {
            // Read fields from parcel and create your object
            return new TareaLista(in);
        }

        @Override
        public TareaLista[] newArray(int size) {
            return new TareaLista[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TareaLista)) return false;
        TareaLista that = (TareaLista) o;
        return isChecked() == that.isChecked() && getIconPriorityColor() == that.getIconPriorityColor() && Objects.equals(getTask(), that.getTask()) && Objects.equals(getPriority(), that.getPriority());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTask(), getPriority(), isChecked(), getIconPriorityColor());
    }
}
