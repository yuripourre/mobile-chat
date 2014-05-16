package br.com.etyllica.mobile.gui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class HorizontalRule extends View {
	
	private int color = 0xAAEEEEEE;
	
	public HorizontalRule(Context context, int color){
		super(context);

		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1));

		setBackgroundColor(color);
	}
	
	public HorizontalRule(Context context){
		this(context, 0xAAEEEEEE);
	}

	public void setColor(int color){

		this.color = color;

		setBackgroundColor(color);

	}

	public int getColor(){

		return color;

	}

}
