package com.example.socialmediaappfirebase;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class viewholders extends RecyclerView.ViewHolder {
    ImageView img2;
    TextView tdesc,tname;
    Button b1;

    public Button getB1() {
        return b1;
    }

    public void setB1(Button b1) {
        this.b1 = b1;
    }

    public viewholders(@NonNull View itemView) {
        super(itemView);
        tdesc = (TextView) itemView.findViewById(R.id.description);
        tname = (TextView) itemView.findViewById(R.id.retrivename);
        img2 = (ImageView) itemView.findViewById(R.id.imageretrive);
        b1=(Button)itemView.findViewById(R.id.button);

    }

    public ImageView getImg2() {
        return img2;
    }

    public TextView getTdesc() {
        return tdesc;
    }

    public TextView getTname() {
        return tname;
    }

    public void setImg2(ImageView img2) {
        this.img2 = img2;
    }

    public void setTdesc(TextView tdesc) {
        this.tdesc = tdesc;
    }

    public void setTname(TextView tname) {
        this.tname = tname;
    }
}
