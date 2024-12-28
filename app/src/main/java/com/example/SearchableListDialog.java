package com.example;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.widget.SearchView;

import com.example.rahim.R;

import java.io.Serializable;
import java.util.List;

public class SearchableListDialog extends DialogFragment implements
        SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private static final String ITEMS = "items";

    private ArrayAdapter<String> listAdapter;

    private ListView listViewItems;

    private SearchableItem searchableItem;

    private OnSearchTextChanged onSearchTextChanged;

    private SearchView searchView;

    private String strTitle;

    private String strPositiveButtonText;

    private DialogInterface.OnClickListener onClickListener;

    public SearchableListDialog() {
        // Default constructor
    }

    public static SearchableListDialog newInstance(List<String> items) {
        SearchableListDialog dialog = new SearchableListDialog();

        Bundle args = new Bundle();
        args.putSerializable(ITEMS, (Serializable) items);

        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View rootView = inflater.inflate(R.layout.searchable_list_dialog, null);

        setData(rootView);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setView(rootView);

        String positiveButtonText = strPositiveButtonText == null ? "CLOSE" : strPositiveButtonText;
        alertDialog.setPositiveButton(positiveButtonText, (dialog, which) -> dialog.dismiss());

        if (!TextUtils.isEmpty(strTitle)) {
            alertDialog.setTitle(strTitle);
        }

        return alertDialog.create();
    }

    private void setData(View rootView) {
        searchView = rootView.findViewById(R.id.search);
        listViewItems = rootView.findViewById(R.id.listItems);

        List<String> items = (List<String>) getArguments().getSerializable(ITEMS);

        if (items != null) {
            listAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items);
            listViewItems.setAdapter(listAdapter);
            listViewItems.setOnItemClickListener((adapterView, view, position, id) -> {
                if (searchableItem != null) {
                    searchableItem.onItemSelected(listAdapter.getItem(position));
                }
                dismiss();
            });
        }

        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (onSearchTextChanged != null) {
            onSearchTextChanged.onTextChanged(query);
        }

        if (listAdapter != null) {
            listAdapter.getFilter().filter(query);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (onSearchTextChanged != null) {
            onSearchTextChanged.onTextChanged(newText);
        }

        if (listAdapter != null) {
            listAdapter.getFilter().filter(newText);
        }
        return false;
    }

    @Override
    public boolean onClose() {
        if (listAdapter != null) {
            listAdapter.getFilter().filter(null);
        }
        return false;
    }

    public interface SearchableItem {
        void onItemSelected(String item);
    }

    public interface OnSearchTextChanged {
        void onTextChanged(String newText);
    }

    public void setSearchableItem(SearchableItem item) {
        this.searchableItem = item;
    }

    public void setOnSearchTextChanged(OnSearchTextChanged listener) {
        this.onSearchTextChanged = listener;
    }

    public void setDialogTitle(String title) {
        this.strTitle = title;
    }

    public void setPositiveButtonText(String text) {
        this.strPositiveButtonText = text;
    }

    public void setPositiveButtonListener(DialogInterface.OnClickListener listener) {
        this.onClickListener = listener;
    }
}
