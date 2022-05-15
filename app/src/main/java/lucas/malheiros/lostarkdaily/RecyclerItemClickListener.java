package lucas.malheiros.lostarkdaily;

import android.content.Context;
import android.os.Debug;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    private RecyclerView recyclerView;
    private OnItemClickListener listenerOnItemClick;
    private GestureDetector gestureDetector;

    public interface OnItemClickListener {
        public void onItemClick(View view, int posicao);

        public void onLongItemClick(View view, int posicao);
    }

    public RecyclerItemClickListener(Context context, RecyclerView recycler, OnItemClickListener listener) {
        recyclerView = recycler;
        listenerOnItemClick = listener;

        gestureDetector = new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {

                        View filho = recyclerView.findChildViewUnder(e.getX(), e.getY());

                        if (filho != null && listenerOnItemClick != null) {

                            Log.d("MENSAGEM",String.valueOf(recyclerView.getChildAdapterPosition(filho)));
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        View filho = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (filho != null && listenerOnItemClick != null) {
                            listenerOnItemClick.onLongItemClick(filho, recyclerView.getChildAdapterPosition(filho));
                        }



                    }
                });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rView, MotionEvent e) {
        return gestureDetector.onTouchEvent(e);
    }

}
