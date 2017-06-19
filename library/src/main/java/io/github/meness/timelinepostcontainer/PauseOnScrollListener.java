/*
 * Copyright 2016 Alireza Eskandarpour Shoferi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.meness.timelinepostcontainer;


import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Listener-helper for {@linkplain AbsListView list views} ({@link ListView}, {@link GridView}) which can
 * {@linkplain ImageLoader#pause() pause ImageLoader's tasks} while list view is scrolling (touch scrolling and/or
 * fling). It prevents redundant loadings.<br />
 * Set it to your list view's {@link AbsListView#setOnScrollListener(AbsListView.OnScrollListener) setOnScrollListener(...)}.<br />
 * This listener can wrap your custom {@linkplain AbsListView.OnScrollListener listener}.
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @since 1.7.0
 */
public class PauseOnScrollListener implements AbsListView.OnScrollListener {

    private final boolean pauseOnScroll;
    private final boolean pauseOnFling;
    private final AbsListView.OnScrollListener externalListener;
    private final ImageLoader imageLoader;

    /**
     * Constructor
     *
     * @param imageLoader   {@linkplain ImageLoader} instance for controlling
     * @param pauseOnScroll Whether {@linkplain ImageLoader#pause() pause ImageLoader} during touch scrolling
     * @param pauseOnFling  Whether {@linkplain ImageLoader#pause() pause ImageLoader} during fling
     */
    public PauseOnScrollListener(ImageLoader imageLoader, boolean pauseOnScroll, boolean pauseOnFling) {
        this(imageLoader, pauseOnScroll, pauseOnFling, null);
    }

    /**
     * Constructor
     *
     * @param imageLoader    {@linkplain ImageLoader} instance for controlling
     * @param pauseOnScroll  Whether {@linkplain ImageLoader#pause() pause ImageLoader} during touch scrolling
     * @param pauseOnFling   Whether {@linkplain ImageLoader#pause() pause ImageLoader} during fling
     * @param customListener Your custom {@link AbsListView.OnScrollListener} for {@linkplain AbsListView list view} which also
     *                       will be get scroll events
     */
    public PauseOnScrollListener(ImageLoader imageLoader, boolean pauseOnScroll, boolean pauseOnFling,
                                 AbsListView.OnScrollListener customListener) {
        this.imageLoader = imageLoader;
        this.pauseOnScroll = pauseOnScroll;
        this.pauseOnFling = pauseOnFling;
        externalListener = customListener;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        switch (i) {
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                imageLoader.resume();
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                if (pauseOnScroll) {
                    imageLoader.pause();
                }
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                if (pauseOnFling) {
                    imageLoader.pause();
                }
                break;
            default:
                // empty, intentional
                break;
        }
        if (externalListener != null) {
            externalListener.onScrollStateChanged(absListView, i);
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        if (externalListener != null) {
            externalListener.onScroll(absListView, i, i1, i2);
        }
    }
}