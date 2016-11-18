package com.example.robert.stockfox;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by robert on 11/6/16.
 */

public class QuoteCursorAdapter extends CursorRecyclerViewAdapter<QuoteCursorAdapter.ViewHolder>
        implements ItemTouchHelperAdapter{

    private static Context mContext;
    private static Typeface robotoLight;
    private boolean isPercent;
    public QuoteCursorAdapter(Context context, Cursor cursor){
        super(context, cursor);
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        robotoLight = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_quote, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final Cursor cursor){
        viewHolder.symbol.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.SYMBOL)));
        viewHolder.bidPrice.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.BID)));
        int sdk = Build.VERSION.SDK_INT;
        double open = Double.parseDouble(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.OPEN)));
        double bid = Double.parseDouble(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.BID)));
        if (bid > open){
            if (sdk < Build.VERSION_CODES.JELLY_BEAN){
                viewHolder.change.setBackgroundDrawable(
                        mContext.getResources().getDrawable(R.drawable.percent_change_pill_green));
            }else {
                viewHolder.change.setBackground(
                        mContext.getResources().getDrawable(R.drawable.percent_change_pill_green));
            }
        } else{
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.change.setBackgroundDrawable(
                        mContext.getResources().getDrawable(R.drawable.percent_change_pill_red));
            } else{
                viewHolder.change.setBackground(
                        mContext.getResources().getDrawable(R.drawable.percent_change_pill_red));
            }
        }

        if (StockFoxUtils.showPercent){
            viewHolder.change.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.PERCENTCHANGE)));
        } else{
            viewHolder.change.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.CHANGE)));
        }
    }

    @Override public void onItemDismiss(int position) {
        Cursor c = getCursor();
        c.moveToPosition(position);
        notifyItemRemoved(position);
        String symbol = c.getString(c.getColumnIndex(DatabaseContract.StockTable.SYMBOL));
        // TODO: Build proper URI method
        mContext.getContentResolver().delete(DatabaseContract.CONTENT_URI.buildUpon().appendPath("symbol").appendPath(symbol).build(), null, null);
        mContext.getContentResolver().notifyChange(DatabaseContract.CONTENT_URI, null);
    }

    @Override public int getItemCount() {
        return super.getItemCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements ItemTouchHelperViewHolder, View.OnClickListener{
        public final TextView symbol;
        public final TextView bidPrice;
        public final TextView change;
        public ViewHolder(View itemView){
            super(itemView);
            symbol = (TextView) itemView.findViewById(R.id.stock_symbol);
            symbol.setTypeface(robotoLight);
            bidPrice = (TextView) itemView.findViewById(R.id.bid_price);
            change = (TextView) itemView.findViewById(R.id.change);
        }

        @Override
        public void onItemSelected(){
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear(){
            itemView.setBackgroundColor(696969);
        }

        @Override
        public void onClick(View v) {

        }
    }
}