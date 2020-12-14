package com.example.sql;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRecord extends RecyclerView.Adapter<AdapterRecord.HolderRecord> {

    private Context context;
    private ArrayList<ModelRecord> recordArrayList;
    private ImageButton btnMore;

    public AdapterRecord(Context context, ArrayList<ModelRecord> recordArrayList) {
        this.context = context;
        this.recordArrayList = recordArrayList;
    }

    @NonNull
    @Override
    public HolderRecord onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_record, parent, false);
        return new HolderRecord(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderRecord holder, int position) {
        ModelRecord modelRecord = recordArrayList.get(position);

        String id = modelRecord.getId();
        String product_name = modelRecord.getProduct_name();
        String image = modelRecord.getImage();
        String brand = modelRecord.getBrand();
        String model = modelRecord.getModel();
        String serialnumber = modelRecord.getSerialnumber();
        String price = modelRecord.getPrice();
        String description = modelRecord.getDescription();
        String TELEFONO = modelRecord.getTELEFONO();

        holder.ivProduct.setImageURI(Uri.parse(image));
        holder.tvProduct.setText(product_name);
        holder.tvPrice.setText(price);

        if (image.equals("null")) {
            holder.ivProduct.setImageResource(R.drawable.ic_launcher_foreground);
        } else {
            holder.ivProduct.setImageURI(Uri.parse(image));
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecordDetailActivity.class);
                intent.putExtra("RECORD_ID", id);
                context.startActivity(intent);
            }
        });

        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreDialog(
                        "" + position,
                        "" + id,
                        "" + product_name,
                        "" + image,
                        "" + brand,
                        "" + model,
                        "" + serialnumber,
                        "" + price,
                        "" + description,
                        "" + TELEFONO
                );


            }
        });

    }

    private void showMoreDialog(String position, String id, String product_name, String image,String TELEFONO, String brand, String model, String serialNumber, String price, String description) {
        String[] options = {"Editar", "Eliminar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent intent = new Intent(context, AddUpdate.class);

                    intent.putExtra("ID", id);
                    intent.putExtra("PRODUCT_NAME", product_name);
                    intent.putExtra("IMAGE", image);
                    intent.putExtra("BRAND", brand);
                    intent.putExtra("MODEL", model);
                    intent.putExtra("SERIALNUMBER", serialNumber);
                    intent.putExtra("PRICE", price);
                    intent.putExtra("DESCRIPTION", description);
                    intent.putExtra("TELEFONO", TELEFONO);
                    intent.putExtra("isEditMode", true);
                    context.startActivity(intent);

                } else if (which == 1) {

                }
            }
        });
        builder.create().show();
    }


    @Override
    public int getItemCount() {
        return recordArrayList.size();
    }


    class HolderRecord extends RecyclerView.ViewHolder {

        ImageView ivProduct;
        TextView tvProduct, tvPrice, tvDescription;
        ImageButton btnMore;


        public HolderRecord(@NonNull View itemView) {
            super(itemView);

            // ivProduct = itemView.findViewById(R.id.civImage2);
            tvProduct = itemView.findViewById(R.id.tvProduct);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnMore = itemView.findViewById(R.id.btnMore);

        }
    }

}