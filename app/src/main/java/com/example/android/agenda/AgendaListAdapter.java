package com.example.android.agenda;


import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.LinkedList;

public class AgendaListAdapter extends RecyclerView.Adapter<AgendaListAdapter.WordViewHolder>  {



    private OnEditListener editListener;
    private View.OnClickListener deleteListener;

    private final LinkedList<Compromisso> mAgendaList;

    private LayoutInflater mInflater;

    public AgendaListAdapter(Context context,
                           LinkedList<Compromisso> agendaList,OnEditListener editListener) {
        mInflater = LayoutInflater.from(context);
        this.mAgendaList = agendaList;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }

    public AgendaListAdapter.WordViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View mItemView = mInflater.inflate(R.layout.lista_agenda_item,
                parent, false);
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, final int position) {
       Compromisso mCurrent = mAgendaList.get(position);
        WordViewHolder personViewHolder = (WordViewHolder)holder;
        holder.CompromissoItem.setText(mCurrent.getTexto1());
        holder.DescricaoItem.setText(mCurrent.getTexto2());
        holder.DataItem.setText(mCurrent.getDate());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                editListener.editItem(mAgendaList.get(position), position);
            }
        });
        holder.b.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {


                int mPosition = position;
                mAgendaList.remove(mPosition);
                notifyItemRemoved(mPosition);
                notifyItemRangeChanged(mPosition, mAgendaList.size());


            }
        });

    }

    @Override
    public int getItemCount() {
        return mAgendaList.size();
    }

    class WordViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        private View view;
        public final TextView CompromissoItem;
        public final TextView DescricaoItem;
        public final TextView DataItem;

        ImageButton b = itemView.findViewById(R.id.Delete);
        ImageButton edit = itemView.findViewById(R.id.CompromissoEdit);
        final AgendaListAdapter mAdapter;

        public WordViewHolder(View itemView, AgendaListAdapter adapter) {
            super(itemView);
            CompromissoItem = itemView.findViewById(R.id.Compromisso);
            DescricaoItem = itemView.findViewById(R.id.Descricao);
            DataItem = itemView.findViewById(R.id.Data);

            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
            b.setOnClickListener(this);
        }
        public View getView(){
            return  view;}

        public void onClick(View v) {


        }
    }

    public void insertItem(Compromisso c) {
       mAgendaList.add(c);
        notifyItemInserted(getItemCount());
    }
}
