package com.example.room;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<MainData> dataList;
    private Activity context;
    private RoomDB database;
    public MainAdapter(Activity context,List<MainData> dataList){
        this.context=context;
        this.dataList=dataList;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,
               parent,false);




        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
      //initialize main data
        MainData data=dataList.get(position);
        //initilazie dtabase
        database= RoomDB.getInstance(context);
        //set text on text view
        holder.text.setText(data.getText());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //inialize main data
                MainData d=dataList.get(holder.getAdapterPosition());
                //get id
                int sid=d.getId();
                //get text
                String sText=d.getText();
                //create dialog
                Dialog dialog=new Dialog(context);
                //set content view
                dialog.setContentView(R.layout.dialor_update);
                //initlze width
                int width= WindowManager.LayoutParams.MATCH_PARENT;
                //inilize highet
                int height=WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.getWindow().setLayout(width,height);
                //show dialog
                dialog.show();
            //// inilize and assign varible
                EditText editText=dialog.findViewById(R.id.editTextTextPersonName2);
                Button btnupdate=dialog.findViewById(R.id.updatebtn);
                //set tetxt on edit text
                editText.setText(sText);
                btnupdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Dismiss dialog
                        dialog.dismiss();
                        //get updat text from edit text
                        String Utext=editText.getText().toString().trim();
                        ///update text in db
                        database.mainDao().update(sid,Utext);
                        //notify when data is updated
                        dataList.clear();
                        dataList.addAll(database.mainDao().getAll());
                        notifyDataSetChanged();
                    }
                });
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             ///inilizae main data
                MainData d=dataList.get(holder.getAdapterPosition());
                //delete text from
                database.mainDao().delete(d);
                //notify when data is deleted
                int postion=holder.getAdapterPosition();
                dataList.remove(postion);
                notifyItemRemoved(position);
                notifyItemChanged(position,dataList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView edit,delete;
        TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            edit=itemView.findViewById(R.id.edit);
            delete=itemView.findViewById(R.id.delete);
            text=itemView.findViewById(R.id.textView);

        }
    }
}
