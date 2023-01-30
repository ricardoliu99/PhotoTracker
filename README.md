# PhotoTracker  
Android mobile app that allows users to take photos, view them, and record their location. You can download the APK of the app here: [link](https://docs.google.com/uc?export=download&id=1VFyhvJHVMwDRoRdVdwSFvmKY5MUtIPVJ)

## Application process
The backend was written in Kotlin and the frontend was written in XML. It consists of one main activity and six fragments. A navigation graph was used for navigating through the different fragments. For data persistence, the Room library was used. FusedLocationProviderClient API from Google Play Services was used for location tracking, Geocoder was used to find out the location name, and Google Maps API was used to provide a map to visually represent the locations. To optimize data loading, the Coil library was used to load the images. CameraX library was used to provide the photo taking functionality.

## Demo
When the user first opens the application, they will prompted to grant access to the device's camera, location and photos. In addition to granting these permissions, the user must turn on their device location manually.

The application consists of two main tabs. First, we have the "New Item" tab in which the user can take a photo by pressing the placeholder image. If the user wants to retake the photo, they can press the image again. Taking a photo automatically saves it to the device, and retaking a photo deletes the previous unsaved photo. Then, the user must provide a title and description for the photo. The following two images show the "New Item" tab before and after it has been filled.
<p align="center">
 <img src="https://raw.githubusercontent.com/ricardoliu99/PhotoTracker/master/examples_images/new_item.jpg?raw=true" height="450">
</p>
<p align="center">
 <img src="https://raw.githubusercontent.com/ricardoliu99/PhotoTracker/master/examples_images/new_item_filled.jpg?raw=true" height="450">
</p>

After pressing save, the new image will be added to the "Photos" tab where the user can see a list of the photos taken along with their title, description, location and date. Each list item also contains three buttons. A location button (left), an edit button (middle) and a delete button (right). The following image shows an example of how the the "Photos" tab looks after adding an item.
<p align="center">
 <img src="https://raw.githubusercontent.com/ricardoliu99/PhotoTracker/master/examples_images/gallery.jpg?raw=true" height="450">
</p>

If the user presses on the location button, the application will open a map to show an approximate location in which the photo was taken as shown below.
<p align="center">
 <img src="https://raw.githubusercontent.com/ricardoliu99/PhotoTracker/master/examples_images/location.jpg?raw=true" height="450">
</p>

If the user presses on the edit button, the application will provide a popup window to change the title and description of the selected photo. The user can either save or cancel this change to exit the window. The following image shows this window.
<p align="center">
 <img src="https://raw.githubusercontent.com/ricardoliu99/PhotoTracker/master/examples_images/edit.jpg?raw=true" height="450">
</p>

The user can delete the photo by pressing the delete button which would also delete it from the device's storage. Finally, the user can long press a list item to show the full image.
