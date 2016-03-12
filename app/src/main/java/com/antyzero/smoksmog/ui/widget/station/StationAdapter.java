package com.antyzero.smoksmog.ui.widget.station;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antyzero.smoksmog.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.malopolska.smoksmog.model.Station;

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.StationItem> {

    private final List<Station> stationList;

    public StationAdapter( List<Station> stationList ) {
        this.stationList = stationList;
    }

    @Override
    public StationItem onCreateViewHolder( ViewGroup parent, int viewType ) {
        return new StationItem( LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_station, parent, false ) );
    }

    @Override
    public void onBindViewHolder( StationItem holder, int position ) {
        holder.textView.setText( stationList.get( position ).getName() );
    }

    @Override
    public int getItemCount() {
        return stationList.size();
    }

    public static class StationItem extends RecyclerView.ViewHolder {

        @Bind( R.id.textView )
        TextView textView;

        public StationItem( View itemView ) {
            super( itemView );
            ButterKnife.bind( this, itemView );
        }
    }
}
