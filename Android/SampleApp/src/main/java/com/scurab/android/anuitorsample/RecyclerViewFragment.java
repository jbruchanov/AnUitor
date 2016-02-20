package com.scurab.android.anuitorsample;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by JBruchanov on 20/02/2016.
 */
public class RecyclerViewFragment extends Fragment {

    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mRecyclerView = new RecyclerView(inflater.getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new SampleAdapter(getContext()));
    }

    static class SampleAdapter extends RecyclerView.Adapter<SampleViewHolder> {

        private String[] mSamples;
        private Context mContext;

        public SampleAdapter(Context context) {
            mContext = context;
            mSamples = new String[3];
            mSamples[0] = context.getString(R.string.lorem_ipsum);
            mSamples[1] = context.getString(R.string.lorem_ipsum_short);
            mSamples[2] = context.getString(R.string.lorem_ipsum_long);
        }

        @Override
        public SampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SampleViewHolder(View.inflate(mContext, R.layout.card_view_list_item, null));
        }

        @Override
        public void onBindViewHolder(SampleViewHolder holder, int position) {
            holder.mOrder.setText(String.valueOf(position + 1));
            holder.mData.setText(mSamples[position % mSamples.length]);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return 30;
        }
    }

    private static class SampleViewHolder extends RecyclerView.ViewHolder{
        final TextView mOrder;
        final TextView mData;

        public SampleViewHolder(View itemView) {
            super(itemView);
            mOrder = (TextView) itemView.findViewById(R.id.order);
            mData = (TextView) itemView.findViewById(R.id.data);
        }
    }
}
