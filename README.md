# Fampay-Assignment

This project aims to demonstrate implementation of simple server-driven-ui. Five different implementations of CardView are available, and can be inflated according to the properties fetched from the server.

## Usage
MainFragment.kt in DynamicUi module can be used to inflate the list of cards, independent of other UI elements. 

```
<androidx.fragment.app.FragmentContainerView
    android:id="@+id/dynamic_ui_fragment"
    android:name="com.adarsh.dynamicui.view.MainFragment"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toTopOf="parent" />
```

### Steps:
- Import DynamicUi module from the project
- Inflate MainFragment from the module in any activity/fragment
