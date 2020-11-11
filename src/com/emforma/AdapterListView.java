package com.emforma;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 
 * Classe responsável por representar o Adapter da ListView
 *
 * @author Álex Prado <alexsilvadoprado@gmail.com>
 * @since 03/11/2014 11:41:44
 * @version 1.0
 */
public class AdapterListView extends BaseAdapter
{
	private LayoutInflater mInflater;
	private ArrayList<ItemListView> itens;
	public AdapterListView(Context context, ArrayList<ItemListView> itens)
	{
		this.itens = itens;
		mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() 
	{
		return itens.size();
	}

	@Override
	public ItemListView getItem(int position) 
	{
		return itens.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) 
	{
		ItemSuporte itemHolder;
		if(view == null)
		{
			view = mInflater.inflate(R.layout.item_listview, null);
			
			itemHolder = new ItemSuporte();
			itemHolder.txtTitle = (TextView) view.findViewById(R.id.text);
			
			view.setTag(itemHolder);
		} else
		{
			itemHolder = (ItemSuporte) view.getTag();
		}
		ItemListView item = itens.get(position);
		itemHolder.txtTitle.setText(item.getTexto());
		
		return view;
	}
	
	private class ItemSuporte
	{
		TextView txtTitle;
	}
}
