package com.emforma;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * Classe responsável por representar a Activity responsável pelo controle
 * da tela layout_tela_dados
 *
 * @author Álex Prado <alexsilvadoprado@gmail.com>
 * @since 03/11/2014 11:44:43
 * @version 1.0
 */
public class TelaDados extends Activity 
{
	Button btnCalcular;
	Button btnSalvar;
	Button btnTempos;
	Context context;
	EditText edtxtAltura;
	EditText edtxtNome;
	EditText edtxtPeso;
	SharedPreferences vrRepositorio;
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tela_dados);
		this.btnSalvar = ((Button)findViewById(R.id.btnSalvar));
		this.btnTempos = ((Button)findViewById(R.id.btnTempos));
		this.btnCalcular = ((Button)findViewById(R.id.btnCalcular));
		this.edtxtNome = ((EditText)findViewById(R.id.edtxtNome));
		this.edtxtPeso = ((EditText)findViewById(R.id.edtxtPeso));
		this.edtxtAltura = ((EditText)findViewById(R.id.edtxtAltura));
		this.context = getApplicationContext();
		this.vrRepositorio = getSharedPreferences("repositorio", 0);
		if (!this.vrRepositorio.getString("nome", "###").equals("###")) 
		{
		  this.edtxtNome.setText(this.vrRepositorio.getString("nome", "###"));
		}
		if (this.vrRepositorio.getFloat("peso", 0.0F) != 0.0F) 
		{
		  this.edtxtPeso.setText(this.vrRepositorio.getFloat("peso", 0.0F) + "");
		}
		if (this.vrRepositorio.getFloat("altura", 0.0F) != 0.0F) 
		{
		  this.edtxtAltura.setText(this.vrRepositorio.getFloat("altura", 0.0F) + "");
		}
    }
    
    /**
     * 
     * Método responsável por tratar o evento onClick dos botões
     * 
     * @param paramView
     * @author Álex Prado <alexsilvadoprado@gmail.com>
     * @since 03/11/2014 11:45:32
     * @version 1.0
     */
    public void eventoBotao(View paramView)
    {
      if (paramView == this.btnSalvar)
      {
        SharedPreferences.Editor localEditor = this.vrRepositorio.edit();
        localEditor.putString("nome", this.edtxtNome.getText().toString());
        localEditor.putFloat("peso", Float.parseFloat(this.edtxtPeso.getText().toString()));
        localEditor.putFloat("altura", Float.parseFloat(this.edtxtAltura.getText().toString()));
        localEditor.commit();
        Toast.makeText(this.context, "Salvo com Sucesso", 0).show();
        return;
      }
      if (paramView == this.btnTempos)
      {
        startActivity(new Intent(this, TelaTempos.class));
        return;
      }
      int i = this.edtxtPeso.getText().length();
      int j = this.edtxtAltura.getText().length();
      if ((i == 0) || (j == 0))
      {
        Animation localAnimation = AnimationUtils.loadAnimation(this, 2130968583);
        this.btnCalcular.startAnimation(localAnimation);
        this.edtxtPeso.startAnimation(localAnimation);
        this.edtxtAltura.startAnimation(localAnimation);
        Toast.makeText(this, "Peso e Altura inválidos", 0).show();
        return;
      }
      float f1 = Float.parseFloat(this.edtxtPeso.getText().toString());
      float f2 = Float.parseFloat(this.edtxtAltura.getText().toString());
      Toast.makeText(this, f1 / (f2 * f2) + "", 1).show();
    }
}
