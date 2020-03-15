package com.b2m.driverlagbe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomApdepterForJobRequest extends BaseAdapter {
    int []DriverImages;
    String []DriverName;
    String []Money;
    String []Distence ;
    String []Pickup_point;
    String []Dropup_point ;
    Button Accept,Cancel;

    Context context;
    LayoutInflater inflater;
    public   class CustomApdepterForJobRequest(Context context, int[]DriverImages, String[]DriverName,String[]Money, String[]Distence,
    String[]Pickup_point,String[]Dropup_point )


    {
        this.context = context;
        this.DriverImages = DriverImages;
        this.DriverName = DriverName;
        this.Money= Money;
        this.Distence = Distence;
        this.Pickup_point=Pickup_point;
        this.Dropup_point=Dropup_point;
    }
       {

    }



    @Override
    public int getCount() {
        return DriverImages.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=  inflater.inflate(R.layout.activity_job_request_,parent,false);

              ImageView imageView=convertView.findViewById(R.id.driver_img_job_request_id);
              TextView name=convertView.findViewById(R.id.name_job_request_textid  );

              TextView money=convertView.findViewById(R.id.money_job_request_txtid);

              TextView distence=convertView.findViewById(R.id.distence_job_request_txtid);

              TextView pickup_point=convertView.findViewById(R.id.pickup_street_jtob_request_txtid);

               TextView dropup_point=convertView.findViewById(R.id.dropup_street_jtob_request_txtid);
               Button Accept=convertView.findViewById(R.id.job_requeset_acceptid);
               Button Cancel=convertView.findViewById(R.id.job_requeset_cancelid);


            imageView.setImageResource(DriverImages[position]);
            name.setText(DriverName[position]);
            money.setText(Money[position]);
            distence.setText(Distence[position]);
            pickup_point.setText(Pickup_point[position]);
            dropup_point.setText(Dropup_point[position]);

        }
        return convertView;
    }
}
