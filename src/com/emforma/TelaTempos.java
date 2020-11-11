package com.emforma;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

/**
 * 
 * Classe respons�vel por representar a Activity respons�vel pelo controle
 * da tela layout_tela_tempos
 *
 * @author �lex Prado <alexsilvadoprado@gmail.com>
 * @since 31/10/2014 03:22:59
 * @version 1.0
 */
public class TelaTempos extends Activity 
{
	Button btnLap;
	Button btnStart;
	Chronometer chronometro;
	boolean startado = false;
	long tempoAoParar = 0L;
	ListView lvTempos;
	AdapterListView adapterList;
	ArrayList<ItemListView> itens;
	long timeElapsed;
	FileOutputStream fos;
	FileInputStream fis;
	String FILENAME = "InternalStorage";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_tela_tempos);
		
		btnStart = ((Button)findViewById(R.id.btnStart));
	    btnLap = ((Button)findViewById(R.id.btnLap));
	    chronometro = ((Chronometer)findViewById(R.id.chrTimer));
	    lvTempos = (ListView) findViewById(R.id.lvTempos);
	    
	    itens = new ArrayList<ItemListView>();
	    btnLap.setEnabled(false);
	    
	    try
		{	
			fis = openFileInput(FILENAME);
			byte[] input = new byte[fis.available()];
			while(fis.read(input) != -1)
			{
				String s = "";
				s += new String(input);
				String [] part = s.split(";");
				for(int i = 0; i < part.length; i++)
				{
					ItemListView ilv = new ItemListView(part[i]);
					itens.add(ilv);
				}
			}
			fis.close();
			createListView();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	    
	    btnStart.setOnClickListener(new View.OnClickListener()
	    {
	      public void onClick(View paramAnonymousView)
	      {
	    	  if (startado)
	    	  {
	    		  timeElapsed = SystemClock.elapsedRealtime() - chronometro.getBase();
		    	  DecimalFormat df = new DecimalFormat("00");
		    	  
		    	  int hours = (int)(timeElapsed / (3600 * 1000));
		    	  int remaining = (int)(timeElapsed % (3600 * 1000));
		    	  
		    	  int minutes = (int)(remaining / (60 * 1000));
		    	  remaining = (int)(remaining % (60 * 1000));
		    	  
		    	  int seconds = (int)(remaining / 1000);
		    	  remaining = (int)(remaining % (1000));
		    	  
		    	  int milliseconds = (int)(((int)timeElapsed % 1000) / 100);
		    	  
		    	  String text = "";
		    	  
		    	  if (hours > 0) 
		    	  {
		    		  text += df.format(hours) + ":";
		    	  }
		    	  
		    	  text += df.format(minutes) + ":";
		    	  text += df.format(seconds) + ":";
		    	  text += Integer.toString(milliseconds);
		    	  
		    	  ItemListView item = new ItemListView(text);
		    	  itens.add(item);
		    	  createListView();
	    		  chronometro.setBase(SystemClock.elapsedRealtime());
	    		  chronometro.stop();
	    		  btnStart.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_start_press));
	    		  btnStart.setTextColor(getResources().getColor(android.R.color.black));
	    		  btnStart.setText("Start");
	    		  btnLap.setEnabled(false);
	    		  startado = false;
	    	  } else
	    	  {
	    		  chronometro.setBase(SystemClock.elapsedRealtime());
		    	  chronometro.start();
		    	  btnStart.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_stop_press));
		    	  btnStart.setTextColor(getResources().getColor(android.R.color.white));
		    	  btnStart.setText("Stop");
		    	  btnLap.setEnabled(true);
		    	  startado = true;
	    	  }
	      }
	    });
	    btnLap.setOnClickListener(new View.OnClickListener()
	    {
	      public void onClick(View paramAnonymousView)
	      {
	    	  timeElapsed = chronometro.getTimeElapsed();
	    	  DecimalFormat df = new DecimalFormat("00");
	    	  
	    	  int hours = (int)(timeElapsed / (3600 * 1000));
	    	  int remaining = (int)(timeElapsed % (3600 * 1000));
	    	  
	    	  int minutes = (int)(remaining / (60 * 1000));
	    	  remaining = (int)(remaining % (60 * 1000));
	    	  
	    	  int seconds = (int)(remaining / 1000);
	    	  remaining = (int)(remaining % (1000));
	    	  
	    	  int milliseconds = (int)(((int)timeElapsed % 1000) / 100);
	    	  
	    	  String text = "";
	    	  
	    	  if (hours > 0) 
	    	  {
	    		  text += df.format(hours) + ":";
	    	  }
	    	  
	    	  text += df.format(minutes) + ":";
	    	  text += df.format(seconds) + ":";
	    	  text += Integer.toString(milliseconds);
	    	  
	    	  ItemListView item = new ItemListView(text);
	    	  itens.add(item);
	    	  createListView();
	    	  chronometro.setBase(SystemClock.elapsedRealtime());
	      }
	    });
	}
	
	/**
	 * 
	 * Método responsável por preencher o nosso ListView com
	 * os dados do nosso ArrayList de objetos ItemListView
	 * 
	 * @author Álex Prado <alexsilvadoprado@gmail.com>
	 * @since 03/11/2014 11:46:37
	 * @version 1.0
	 */
	private void createListView()
	{
		adapterList = new AdapterListView(this, itens);
		lvTempos.setAdapter(adapterList);
		lvTempos.setCacheColorHint(Color.TRANSPARENT);
		
		try
		{
			fos = openFileOutput(FILENAME, MODE_PRIVATE);
			for(ItemListView ilv : itens)
			{
				fos.write(ilv.getTexto().getBytes());
				fos.write(";".getBytes());
			}
			fos.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
