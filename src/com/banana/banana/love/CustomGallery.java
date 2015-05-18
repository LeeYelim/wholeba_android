package com.banana.banana.love;

import com.banana.banana.MyApplication;
import com.banana.banana.R;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

public class CustomGallery extends Gallery{
	   private static Camera mCamera;
	  
	 
	    public CustomGallery(Context context) {
	          this(context, null);
	    }
	 
	      public CustomGallery(Context context, AttributeSet attrs) {
	           this(context, attrs, 0);
	      }
	   
	        public CustomGallery(Context context, AttributeSet attrs, int defStyle) {
	            super(context, attrs, defStyle);
	    
	           mCamera = new Camera();
	          setSpacing(60);
	       
	      }
	   
	     protected boolean getChildStaticTransformation(View child, Transformation t) {
	    	
				
				//super.onWindowFocusChanged(hasFocus);
	    	 final int mCenter = (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
	         
        final int childCenter = child.getLeft() + child.getWidth() / 2;
	          final int childWidth = child.getWidth();
	 
	          t.clear();
	          t.setTransformationType(Transformation.TYPE_MATRIX);
	         float rate = Math.abs((float) (mCenter - childCenter) / childWidth);
	  
	           mCamera.save();
	            final Matrix matrix = t.getMatrix();
	           float zoomAmount = (float) (rate * 100.0);
	           mCamera.translate(0.0f, 0.0f, zoomAmount);
	           mCamera.getMatrix(matrix);
	            matrix.preTranslate(-(childWidth / 2), -(childWidth / 2));
	          matrix.postTranslate((childWidth / 2), (childWidth / 2));
	          mCamera.restore();
	    
	            return true;
	     }
	 /*	public void onWindowFocusChanged(boolean hasFocus) {
			// TODO Auto-generated method stub
	 		
			ImageView imageView=(ImageView)v;
			float left = imageView.getLeft();
			float originTop = imageView.getTop();
			float originRight = imageView.getRight();
			float originBottom = imageView.getBottom();
			Toast.makeText(MyApplication.getContext(), "left:"+left+"right:"+originRight+"top:"+originTop+"bottom:"+originBottom, Toast.LENGTH_LONG).show();
			super.onWindowFocusChanged(hasFocus);
		}*/
}
