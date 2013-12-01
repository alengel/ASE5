package com.team5.courseassignment.data;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.team5.courseassignment.R;

public class NumberPickerDialogPreference extends DialogPreference {
	/**
	 * Setting default values.
	 */
	private static final int DEFAULT_MIN_VALUE = 0;
	private static final int DEFAULT_MAX_VALUE = 100;
	private static final int DEFAULT_VALUE = 0;

	private int mMinValue;
	private int mMaxValue;
	private int mValue;

	/**
	 * Setting number picker.
	 */
	private NumberPicker mNumberPicker;

	/**
	 * Constructor method of number picker dialog preference.
	 * 
	 * @param context
	 *            - The context.
	 */
	public NumberPickerDialogPreference(Context context) {
		this(context, null);
	}

	/**
	 * Constructor method of number picker dialog preference with attributes.
	 * Getting attributes specified in xml and setting positive|negative buttons
	 * with string labels.
	 * 
	 * @param context
	 *            - The context.
	 * @param attrs
	 *            - Attributes specified in xml.
	 */
	public NumberPickerDialogPreference(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.NumberPickerDialogPreference, 0, 0);
		try {
			setMinValue(a.getInteger(
					R.styleable.NumberPickerDialogPreference_min,
					DEFAULT_MIN_VALUE));
			setMaxValue(a.getInteger(
					R.styleable.NumberPickerDialogPreference_android_max,
					DEFAULT_MAX_VALUE));
		} finally {
			a.recycle();
		}

		// set layout
		setDialogLayoutResource(R.layout.numberpicker_dialog);
		setPositiveButtonText(android.R.string.ok);
		setNegativeButtonText(android.R.string.cancel);
		setDialogIcon(null);
	}

	/**
	 * On setting initial value of number picker dialog. (Integer)
	 */
	@Override
	protected void onSetInitialValue(boolean restore, Object defaultValue) {
		setValue(restore ? getPersistedInt(DEFAULT_VALUE)
				: (Integer) defaultValue);
	}

	/**
	 * On getting default value of number picker dialog.
	 * 
	 * @param TypeArray
	 *            a - The type array.
	 */
	@Override
	protected Object onGetDefaultValue(TypedArray a, int index) {
		return a.getInt(index, DEFAULT_VALUE);
	}

	/**
	 * Binding dialog view which connects textView and numberPicker, and sets
	 * Min|Max values of the picker.
	 */
	@Override
	protected void onBindDialogView(View view) {
		super.onBindDialogView(view);

		TextView dialogMessageText = (TextView) view
				.findViewById(R.id.text_dialog_message);
		dialogMessageText.setText(getDialogMessage());

		mNumberPicker = (NumberPicker) view.findViewById(R.id.number_picker);
		mNumberPicker.setMinValue(mMinValue);
		mNumberPicker.setMaxValue(mMaxValue);
		mNumberPicker.setValue(mValue);
	}

	/**
	 * Getter method for retrieving minimum value of picker.
	 * 
	 * @return mMinValue;
	 */
	public int getMinValue() {
		return mMinValue;
	}

	/**
	 * Setter method for posting minimum value of picker.
	 * 
	 * @param minValue
	 *            - in case if user wants to change minimum value of the number
	 *            picker.
	 */
	public void setMinValue(int minValue) {
		mMinValue = minValue;
		setValue(Math.max(mValue, mMinValue));
	}

	/**
	 * Getter method for retrieving maximum value of picker.
	 * 
	 * @return mMaxValue;
	 */
	public int getMaxValue() {
		return mMaxValue;
	}

	/**
	 * Setter method for posting maximum value of picker.
	 * 
	 * @param mMaxValue
	 *            - in case if user wants to change maximum value of the number
	 *            picker.
	 */
	public void setMaxValue(int maxValue) {
		mMaxValue = maxValue;
		setValue(Math.min(mValue, mMaxValue));
	}

	/**
	 * Getter method for retrieving value of the picker.
	 * 
	 * @return mValue;
	 */
	public int getValue() {
		return mValue;
	}

	/**
	 * Setter method for posting value of the picker.
	 * 
	 * @param value
	 *            - in case if user wants to change the value of the number
	 *            picker.
	 */
	public void setValue(int value) {
		value = Math.max(Math.min(value, mMaxValue), mMinValue);

		if (value != mValue) {
			mValue = value;
			persistInt(value);
			notifyChanged();
		}
	}

	/**
	 * Called when the dialog is dismissed and should be used to save data to
	 * the Shared Preferences.
	 */
	@Override
	protected void onDialogClosed(boolean positiveResult) {
		super.onDialogClosed(positiveResult);

		// when the user selects "OK", persist the new value
		if (positiveResult) {
			int numberPickerValue = mNumberPicker.getValue();
			if (callChangeListener(numberPickerValue)) {
				setValue(numberPickerValue);
			}
		}
	}

	/**
	 * This method returns Parcelable object containing the current dynamic
	 * state of this Preference, or null if there is nothing to save. The
	 * default implementation returns null.
	 */
	@Override
	protected Parcelable onSaveInstanceState() {
		// save the instance state so that it will survive screen orientation
		// changes and other events that may temporarily destroy it
		final Parcelable superState = super.onSaveInstanceState();

		// set the state's value with the class member that holds current
		// setting value
		final SavedState myState = new SavedState(superState);
		myState.minValue = getMinValue();
		myState.maxValue = getMaxValue();
		myState.value = getValue();

		return myState;
	}

	/**
	 * This method allows a Preference to re-apply a representation of its
	 * internal state that had previously been generated by
	 * onSaveInstanceState(). This function will never be called with a null
	 * state.
	 */
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		// check whether we saved the state in onSaveInstanceState()
		if (state == null || !state.getClass().equals(SavedState.class)) {
			// didn't save the state, so call superclass
			super.onRestoreInstanceState(state);
			return;
		}

		// restore the state
		SavedState myState = (SavedState) state;
		setMinValue(myState.minValue);
		setMaxValue(myState.maxValue);
		setValue(myState.value);

		super.onRestoreInstanceState(myState.getSuperState());
	}

	/**
	 * This sub class stores default values. Min|Max.
	 */
	private static class SavedState extends BaseSavedState {
		int minValue;
		int maxValue;
		int value;

		/**
		 * This is the constructor method of SavedState with Parcelable.
		 * 
		 * @param superState
		 */
		public SavedState(Parcelable superState) {
			super(superState);
		}

		/**
		 * This is the constructor method of SavedState with Parcel.
		 * 
		 * @param source
		 */
		public SavedState(Parcel source) {
			super(source);

			minValue = source.readInt();
			maxValue = source.readInt();
			value = source.readInt();
		}

		/**
		 * Flatten object in to a Parcel.
		 * 
		 * @params dest - the parcel in which the object should be written -
		 *         Parcel
		 * @params flags -additional flags about how the object should be
		 *         written -int
		 */
		@Override
		public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);

			dest.writeInt(minValue);
			dest.writeInt(maxValue);
			dest.writeInt(value);
		}

		@SuppressWarnings("unused")
		public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
			@Override
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}

			@Override
			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};
	}
}
