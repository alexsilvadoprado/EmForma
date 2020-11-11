package com.emforma;

/**
 * 
 * Classe responsável por representar cada item que será
 * adicionado ao ListView
 *
 * @author Álex Prado <alexsilvadoprado@gmail.com>
 * @since 03/11/2014 11:43:53
 * @version 1.0
 */
public class ItemListView 
{
	private String texto;
	
	public ItemListView()
	{
	}
	
	public ItemListView(String texto)
	{
		this.texto = texto;
	}

	public String getTexto() 
	{
		return texto;
	}

	public void setTexto(String texto) 
	{
		this.texto = texto;
	}
}
