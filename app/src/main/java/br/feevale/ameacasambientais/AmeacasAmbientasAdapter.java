package br.feevale.ameacasambientais;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AmeacasAmbientasAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    BancoDados db;

    public AmeacasAmbientasAdapter(BancoDados db, Context ctx) {
        this.db = db;
        this.layoutInflater = LayoutInflater.from(ctx);
    }


    @Override
    public int getCount() {
        return db.getAmeacasAmbientais().size();
    }

    @Override
    public Object getItem(int i) {
        return db.getAmeacasAmbientais().get(i);
    }

    @Override
    public long getItemId(int i) {
        return db.getAmeacasAmbientais().get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.ameacas_ambientais_item, null);
        TextView lbDescricao = v.findViewById(R.id.lbDescricao);
        TextView lbData = v.findViewById(R.id.lbData);
        lbDescricao.setText(db.getAmeacasAmbientais().get(i).getDescricao());
        lbData.setText(db.getAmeacasAmbientais().get(i).getData());
        return v;
    }
}



