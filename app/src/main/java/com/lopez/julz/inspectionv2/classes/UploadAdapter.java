package com.lopez.julz.inspectionv2.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lopez.julz.inspectionv2.R;
import com.lopez.julz.inspectionv2.api.ServiceConnectionInspections;
import com.lopez.julz.inspectionv2.database.AppDatabase;
import com.lopez.julz.inspectionv2.database.LocalServiceConnectionInspections;
import com.lopez.julz.inspectionv2.database.LocalServiceConnections;

import java.util.List;

public class UploadAdapter extends RecyclerView.Adapter<UploadAdapter.ViewHolder> {

    public List<LocalServiceConnections> localServiceConnectionsList;
    public Context context;
    public AppDatabase db;
    private OnItemClickListener listener;

    public UploadAdapter(List<LocalServiceConnections> localServiceConnectionsList, Context context, AppDatabase db, OnItemClickListener listener) {
        this.localServiceConnectionsList = localServiceConnectionsList;
        this.context = context;
        this.db = db;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UploadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.upload_recyclerview_layout, parent, false);

        UploadAdapter.ViewHolder viewHolder = new UploadAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UploadAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        LocalServiceConnections localServiceConnections = localServiceConnectionsList.get(position);

        GetInspection inspGet = new GetInspection(localServiceConnections.getId(), position, holder);
        inspGet.execute();

        holder.download_recyclerview_name.setText(localServiceConnections.getServiceAccountName());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return localServiceConnectionsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView download_recyclerview_name, status;
        public FloatingActionButton delete;
        public CoordinatorLayout archiveLayoutParent;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            download_recyclerview_name = (TextView) itemView.findViewById(R.id.download_recyclerview_name);
            status = (TextView) itemView.findViewById(R.id.status);
            delete = (FloatingActionButton) itemView.findViewById(R.id.delete);
            archiveLayoutParent = (CoordinatorLayout) itemView.findViewById(R.id.archiveLayoutParent);
        }
    }

    public void removeItem(int position) {
        localServiceConnectionsList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, localServiceConnectionsList.size());
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class GetInspection extends AsyncTask<Void, Void, Void> {

        public String svcId;
        public int pos;
        public ViewHolder vh;
        public LocalServiceConnectionInspections inspections;
        public GetInspection(String svcId, int pos, ViewHolder vh) {
            this.svcId = svcId;
            this.pos = pos;
            this.vh = vh;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                inspections = null;
                inspections = db.serviceConnectionInspectionsDao().getOneBySvcId(svcId);
                Log.e("TEST", svcId);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERR_GET_INSP", e.getMessage());
                inspections = null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            try {
                if (inspections != null) {
                    vh.status.setText(inspections.getStatus());
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERR_DISP_INSP", e.getMessage());
            }
        }
    }
}
