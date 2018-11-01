package com.tokg.doubleclicktest;

import android.os.Looper;
import android.support.annotation.CheckResult;
import android.view.View;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

import static com.tokg.doubleclicktest.RxView.Preconditions.checkNotNull;
import static com.tokg.doubleclicktest.RxView.Preconditions.checkUiThread;
/**
 * Created by TokG on 18/11/11.
 */
public class RxView {

        /**
         * 防止重复点击
         *
         * @param target 目标view
         * @param action 监听器
         */
        public static void setOnClickListeners(final Action1<View> action, @NonNull View... target) {
            for (View view : target) {
                RxView.onClick(view).throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe(new Consumer<View>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull View view) throws Exception {
                        action.onMyClick(view);
                    }
                });
            }
        }


    @CheckResult
    @NonNull
    private static Observable<View> onClick(@NonNull View view) {
        checkNotNull(view, "view == null");
        return Observable.create(new ViewClickOnSubscribe(view));
    }
    private static class ViewClickOnSubscribe implements ObservableOnSubscribe<View> {
        private View view;

        public ViewClickOnSubscribe(View view) {
            this.view = view;
        }

        @Override
        public void subscribe(@io.reactivex.annotations.NonNull final ObservableEmitter<View> e) throws Exception {
            checkUiThread();

            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!e.isDisposed()) {
                        e.onNext(view);
                    }
                }
            };
            view.setOnClickListener(listener);
        }

    }
    public interface Action1<T> {
        void onMyClick(T t);
    }

    public static final class Preconditions {
        public static void checkArgument(boolean assertion, String message) {
            if (!assertion) {
                throw new IllegalArgumentException(message);
            }
        }

        public static <T> T checkNotNull(T value, String message) {
            if (value == null) {
                throw new NullPointerException(message);
            }
            return value;
        }

        public static void checkUiThread() {
            if (Looper.getMainLooper() != Looper.myLooper()) {
                throw new IllegalStateException(
                        "Must be called from the main thread. Was: " + Thread.currentThread());
            }
        }

        private Preconditions() {
            throw new AssertionError("No instances.");
        }
    }

}
