package com.teamshor.audiometry;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamshor.audiometry.activities.BargraphActivity;
import com.teamshor.audiometry.activities.TestActivity;
import com.teamshor.audiometry.fragments.FilledTestFragment;

import java.util.ArrayList;

public class Results extends RecyclerView.Adapter<Results.ViewHolder> {

    // creating a variable for array list and context.
    private ArrayList<ResultModal> resultModalArayList;
    private Context context;

    // creating a constructor for our variables.
    public Results(ArrayList<ResultModal> resultModalArayList, Context context) {
        this.resultModalArayList = resultModalArayList;
        this.context = context;
    }

    @NonNull
    @Override
    public Results.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Results.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        ResultModal modal = resultModalArayList.get(position);
        holder.time.setText(modal.getTime());
        holder.date.setText(modal.getDate());
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FilledTestFragment.activity, BargraphActivity.class);
                intent.putExtra("Date", modal.getDate());
                intent.putExtra("Time", modal.getTime());
                FilledTestFragment.activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return resultModalArayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private TextView time, date;
        private ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initializing our views with their ids.
            time = itemView.findViewById(R.id.item_time);
            date = itemView.findViewById(R.id.item_date);
            img= itemView.findViewById(R.id.item_image);
        }
    }

    public static String getStamp(String date, String time)
    {
        String stamp="";
        String res=date+time;

        for(int i=0;i<(date.length()+time.length());i++)
        {
            char ch=res.charAt(i);

            if((int)ch >=48 && (int)ch <=57)
                stamp+=ch;
        }
        return stamp;
    }
}
