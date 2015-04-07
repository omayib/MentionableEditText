package id.co.technomotion.mention.tutorial;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;


public class MentionableEditText extends MultiAutoCompleteTextView implements
		OnItemClickListener {

	public MentionableEditText(Context context) {
		super(context);
		setOnItemClickListener(this);
		addTextChangedListener(watcher);
	}

	public MentionableEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnItemClickListener(this);
		addTextChangedListener(watcher);
		init(attrs);
	}

	public MentionableEditText(Context context, AttributeSet attrs,
                               int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setOnItemClickListener(this);
		addTextChangedListener(watcher);
		init(attrs);
	}

	private void init(AttributeSet attrs) {
		setText(getText());
	}

	@Override
	protected void onTextChanged(CharSequence text, int start,
			int lengthBefore, int lengthAfter) {
		super.onTextChanged(text, start, lengthBefore, lengthAfter);
	}


	private final TextWatcher watcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
		}
	};

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		setMention();
	}

	private void setMention() {
		SpannableStringBuilder ssb = new SpannableStringBuilder(getText());
		// split string which space
		String chips[] = getText().toString().trim().split(" ");
		int x = 0;

		for (String c : chips) {
			// inflate chips_edittext layout
			LayoutInflater lf = (LayoutInflater) getContext().getSystemService(
					Activity.LAYOUT_INFLATER_SERVICE);
			TextView textView = (TextView) lf.inflate(
					R.layout.item_mention, null);
			textView.setText(c); // set text
			x = x + c.length() + 1;
		}
		// set chips span
		setText(ssb);
		// move cursor to last
		setSelection(getText().length());
	}

	@Override
	public <T extends ListAdapter & Filterable> void setAdapter(T adapter) {
		ArrayAdapter<People> person = (ArrayAdapter<People>) adapter;

		List<String> username = new ArrayList<String>();
		for (int i = 0; i < person.getCount(); i++) {
			username.add("@" + person.getItem(i).getName());
		}
		ArrayAdapter<String> usernameAdapter = new ArrayAdapter<String>(
				getContext(), android.R.layout.simple_dropdown_item_1line,
				username) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View v = super.getView(position, convertView, parent);
				TextView t = (TextView) v.findViewById(android.R.id.text1);
				t.setTextColor(Color.BLACK);
				return v;
			}
		};

		super.setAdapter(usernameAdapter);
	}

}
