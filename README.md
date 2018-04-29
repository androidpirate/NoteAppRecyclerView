# Building a Note Taking App

In this tutorial series, we are going to build a fully functional note-taking app **(at least not another ToDoList app)** using some of the most common building blocks of **Android framework**, such as **Activities, Fragments, intents, SQLite, RecyclerView etc.**  to demonstrate how these building blocks are tied together and interact with each other.

If you have no experience building an Android app before, you may want to check out some of the links from in **Resources** section to get yourself started. Otherwise, I will assume you have some prior experience in developing Android applications.

Without  further due…



## NoteApp RecyclerView – Tutorial 1

Start by downloading cloning/forking the app from the link below:

[**NoteAppRecyclerView - Tutorial 1**](https://github.com/androidpirate/NoteAppRecyclerView "****NoteAppRecyclerView - Tutorial 1****")



### Goal of This Tutorial

The goal of this tutorial is to display a list of notes, using a **RecyclerView** and a **RecyclerView Adapter**. We will use a static list of notes as our source of data. You can find further information about how **RecyclerView** works [**here**](https://developer.android.com/guide/topics/ui/layout/recyclerview.html "**here**") and advantage of using it [**here**](https://medium.com/wolox-driving-innovation/the-good-the-bad-and-the-ugly-things-about-the-new-recyclerview-1795df6f94c9 "**here**").

**RecyclerView** provides a lot of flexibility when it comes to its implementation, which allows us to customize it however we like. On the other hand, all that good stuff also means we have to implement most of it ourselves. **With great power, comes … yeah, you guess it right, great boilerplate code. (But this one’s worth it !!!)**



### What’s in Starter Module?

Starter module already has**POJO Note class** that represents our model and **FakeDataUtils class** which provides our static data for this tutorial.

If you want to give it a shot yourself, you can follow the steps below and if you stuck at some point, check out the solution module or the rest of the tutorial.



### Steps to Build

1. Add RecyclerView dependencies to **build.gradle (app)**
2. Add a **RecyclerView** element to **main_activity.xml**
3. Create **a new layout resource** for list items, **list_item.xml**
4. Design list item layout to display note title and description as **TextView**
5. Create a **new package: adapter**
6. Add a **NoteAdapter class** in adapter package which extends **RecyclerView.Adapter**
7. In **MainActivity** create a field for a list of notes and a reference to RecyclerView
8. In **onCreate() method of MainActivity**, use **FakeDataUtil’s static getNotes() method** to store the list of notes into local list
9. Get a reference to **RecyclerView, set its Layout Manager**
10. Create a new instance of NoteAdapter and set as RecyclerView adapter



### Adding App Level Dependencies

RecyclerView is part of the **support library** and we need to add the dependencies to our **app level build.gradle file** in order to use it in our app.

Open up your **build.gradle file** and insert the following (or the latest) RecyclerView dependency as shown below:


```xml
dependencies {
  .
  .
   implementation 'com.android.support:recyclerview-v7:27.1.1'
}
```


Once any changes are made to **build.gradle file**, it has to be synced, so don’t forget to **sync it**.



### The Layout

Open up **activity_main.xml** layout file located under **res directory** on your project pane. Replace the default **ConstraintLayout with a RelativeLayout (or if you can stick with it if you feel more comfortable with it)**, and add a RecyclerView as the only child for RelativeLayout:


```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
   xmlns:android="http://schemas.android.com/apk/res/android"
   xmlns:tools="http://schemas.android.com/tools"
   android:layout_width="match_parent"
   android:layout_height="match_parent"
   tools:context="com.example.android.recyclerviewbasics.MainActivity">

   <android.support.v7.widget.RecyclerView
       android:id="@+id/rv_note_list"
       android:layout_width="match_parent"
       android:layout_height="match_parent"/>

</RelativeLayout>
```


Create a new layout file for list items under the same **res directory**. **(I named mine list_item.xml, I mean who doesn’t ??!)** Open the file and start designing your layout for a single list item. Since we only have two fields in **Note class**, all we need two **TextView** elements to display the data:


```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
   xmlns:android="http://schemas.android.com/apk/res/android"
   android:layout_width="match_parent"
   android:layout_height="wrap_content">

   <TextView
       android:id="@+id/tv_title"
       android:layout_width="match_parent"
       android:layout_height="wrap_content"
       android:textColor="@android:color/black"
       android:textSize="18sp"
       android:textStyle="bold"
       android:layout_marginLeft="16dp"
       android:layout_marginRight="16dp"
       android:layout_marginTop="4dp"
       android:layout_marginBottom="4dp"/>

   <TextView
       android:id="@+id/tv_description"
       android:layout_width="match_parent"
       android:layout_height="wrap_content"
       android:textColor="@android:color/black"
       android:textSize="16sp"
       android:layout_below="@id/tv_title"
       android:layout_marginLeft="16dp"
       android:layout_marginRight="16dp"
       android:layout_marginTop="4dp"
       android:layout_marginBottom="4dp"/>

   <!-- Line separator -->
   <FrameLayout
       android:layout_width="match_parent"
       android:layout_height="1dp"
       android:background="@android:color/black"
       android:layout_below="@id/tv_description"
       android:layout_marginTop="4dp"
       android:layout_marginBottom="4dp"/>

</RelativeLayout>
```


I used a **FrameLayout** to represent a line separator at the bottom of the layout. **(What a cheap trick !)** We will make it look fancier in upcoming tutorials!  



### RecyclerView Adapter

**RecyclerView Adapter** converts the list of objects into item views and populates **RecyclerView**.

Create a **new package for adapter** and add **NoteAdapter class** to the package. Make sure **NoteAdapter class extends the RecyclerView.Adapter<RecyclerView.NoteHolder>**.

You might be wondering what’s **RecyclerView.NoteHolder ?** Well, the adapter uses **ViewHolder(s)** to create list items, specifically speaking to create item views. That’s why the adapter has to specify a ViewHolder class which will handle creating those views. Usually, **ViewHolder class** is implemented as an inner class in **NoteAdapter class** as they go along with each other. (Need more info about **RecyclerView Adapter**, lookie [**here**](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter "**here**")):

```java
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

   class NoteHolder extends RecyclerView.ViewHolder {

   }
}
```


At this point, we need a constructor to instantiate our adapter which gets a list of notes as an argument and three basic methods which has to be implemented:

- **onCreateViewHolder()**
- **onBindViewHolder()**
- **getItemCount()**

** From the official documentation: **

abstract  | onCreateViewHolder(ViewGroup parent, int viewType)
------------- | -------------
VH  | Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.


abstract  | onBindViewHolder(VH holder, int position)
------------- | -------------
void | Called by RecyclerView to display the data at the specified position.


abstract  | getItemCount()
------------- | -------------
int | Returns the total number of items in the dataset held by the adapter.

Let’s start with a list to hold notes and the constructor:


```java
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
   private List<Note> notes;

   public NoteAdapter(List<Note> notes) {
       this.notes = notes;
   }

   // ViewHolder class is excluded for simplicity
}
```


Now that we defined our list and instantiate it with a list of notes passed as an argument to the adapter constructor, it is time to implement the required methods mentioned above:


```java
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
   // Fields and constructor excluded for simplicity
   @Override
   public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // Here we create the list item view, by using LayoutInflater
       View itemView = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.list_item, parent, false);
       // And pass it to ViewHolder as an argument
       return new NoteHolder(itemView);
   }

   @Override
   public void onBindViewHolder(NoteHolder holder, int position) {
       // We use a private bindNote() method to pass the note in position to ViewHolder
       // bindNote() method will be implemented in ViewHolder class
       holder.bindNote(notes.get(position));
   }

   @Override
   public int getItemCount() {
       // Returns the size of the list
       if(notes.size() == 0) {
           return 0;
       } else {
           return notes.size();
       }
   }
   // ViewHolder class is excluded for simplicity
}
```


Above is how the adapter creates each **ViewHolder** for each item in the list. In some examples, you might have seen a **Context** is also passed to the adapter constructor to be able to inflate the layout file, but here we get **Context** on the fly, which is a good practice to not to hold on to a **Context instance for a long time** since it might cause memory leaks. (More on how **Context** causes memory leaks [**here**](https://android-developers.googleblog.com/2009/01/avoiding-memory-leaks.html "**here**"))

**getItemCount() method** simply returns the number of items in adapter’s data set, it returns 0 if the data set is empty.

Once the **ViewHolder** is created, the adapter calls **onBindViewHolder() method**, and binds the data from the next note in the list to the view.

We will see how the data is bind to view when we implement **bindNote() method in NoteHolder class**.

**RecyclerView** only displays part of the list at any given time, which means the views are not visible are recycled as we scroll up and down the list. That’s how **RecyclerView** achieves faster scroll speeds without lagging and it uses less memory to do so. Once the list items are bind with views, all **RecyclerView** needs to do is to recall the view that’s bind to the particular list item and display them as they appear on the screen.

Now the adapter is created, we can move on to implement the **NoteHolder class**. We also need a constructor to instantiate NoteHolder, along with references to **TextView elements in list_item.xml**.


```java
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    // Fields, constructor, and methods are excluded for simplicity
    class NoteHolder extends RecyclerView.ViewHolder {
        // Add fields for title TextView and description TextView in list_item.xml
        private TextView title;
        private TextView description;

        public NoteHolder(View itemView) {
            super(itemView);
            // Get a reference for both TextView
            title = itemView.findViewById(R.id.tv_title);
            description = itemView.findViewById(R.id.tv_description);
         }

        public void bindNote(Note note) {
            // Check if note fields are not null
            if(note.getTitle() != null && note.getDescription() != null) {
                // Set text for TextViews
                title.setText(note.getTitle());
                description.setText(note.getDescription());
            } else {
                throw new NullPointerException("Note fields are null.");
            }
        }
    }
}
```


We defined two fields: **a title TextView and a description TextView**. In the constructor, we get the references for both views using itemView which we passed from the adapter.

The magic happens in the **bindNote() method**. It gets a **Note object as an argument**, which is the next note in the list passed by **NoteAdapter**. Here we set the text for both title and description if the fields are not null and each list item is created just like that!



### The RecyclerView

Finally, it all comes to display the data in our **RecyclerView**. Remember, we inserted a **RecyclerView** child in **MainActivity’s layout file, activity_main.xml** earlier in this tutorial. Now, all we need to do is to get a reference to it and set it up:


```java
public class MainActivity extends AppCompatActivity{
   private List<Note> notes;
   // Create a field for RecyclerView
   private RecyclerView recyclerView;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       // Get static data
       notes = FakeDataUtils.getFakeNotes();
       // Get a reference to RecyclerView
       recyclerView = findViewById(R.id.rv_note_list);
       // Set Layout Manager
       recyclerView.setLayoutManager(new LinearLayoutManager(this));
       // Set adapter
       NoteAdapter adapter = new NoteAdapter(notes, this);
       recyclerView.setAdapter(adapter);
   }
}
```


We first create a field for **RecyclerView.(At this point, you can also define it as a local variable)**. And use the **infamous findViewById() method** to get a reference for our **RecyclerView**. Set the **LayoutManager as LinearLayoutManager and pass in MainActivity as Context.** And finally, we get an instance of **NoteAdapter** and set it as **RecyclerView Adapter**.

And that's it! Run the app and check it out yourself!



### What's in The Next Tutorial

In the [**next tutorial**](https://androidpirate.github.io/NoteAppCardView/ "**next tutorial**"), we are going to add CardView elements to add a sophisticated material design look in our **Note Taking App** and make it respond to click events !!!



### Resources
1. [Android Developer Guides](https://developer.android.com/guide/ "Android Developer Guides") by Google
2. [Android Cliffnotes](https://guides.codepath.com/android "Android Cliffnotes") by Codepath
