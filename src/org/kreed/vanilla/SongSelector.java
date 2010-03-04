/*
 * Copyright (C) 2010 Christopher Eby <kreed@kreed.org>
 *
 * This file is part of Vanilla Music Player.
 *
 * Vanilla Music Player is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Library General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Vanilla Music Player is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.kreed.vanilla;

import org.kreed.vanilla.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class SongSelector extends Activity implements AdapterView.OnItemClickListener, TextWatcher {
	private SongAdapter mAdapter;
	private ListView mListView;
	private TextView mTextView;

	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);

		setContentView(R.layout.song_selector);

		mAdapter = new SongAdapter(this);

		mListView = (ListView)findViewById(R.id.song_list);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);

		mTextView = (TextView)findViewById(R.id.filter_text);
		mTextView.addTextChangedListener(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (super.onKeyDown(keyCode, event))
			return true;

		mTextView.requestFocus();
		return mTextView.onKeyDown(keyCode, event);
	}

	public void onItemClick(AdapterView<?> list, View view, int pos, long id)
	{
		Intent intent = new Intent(this, PlaybackService.class);
		intent.putExtra("songId", (int)id);
		startService(intent);
	}

	public void afterTextChanged(Editable editable)
	{
	}

	public void beforeTextChanged(CharSequence s, int start, int count, int after)
	{
	}

	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
		mAdapter.getFilter().filter(s);
	}
}