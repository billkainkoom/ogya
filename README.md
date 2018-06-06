#Ogya
## QuickList (Listable Adapter)


## Quick Dialog

Quick dialog simply gives you multiple consistent variants of dialogs you need in your  Android App.

  - Message Dialog
  - Progress Dialog
  - Alert Dialog
  - Input Dialog
  
  ### Quick Start

Step 1. Add it in your root build.gradle at the end of repositories:
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Step 2. Add the dependency
```gradle
	dependencies {
	        implementation 'com.github.billkainkoom:quickdialog:v0.70'
}
```


### Message Dialog
 A message dialog simply displays an image with one button.

 ```kotlin
  QuickDialog(
                context = this,
                style = QuickDialogType.Message,
                title = "Hello World",
                message = "The quick dialog jumped over the old dialog",
                image = R.drawable.ic_info_outline_black_24dp)
                .overrideButtonNames("OK" ).overrideClicks({ ->
                    Toast.makeText(context, "Clicked on OK", Toast.LENGTH_SHORT).show()
                }).show()
 ```

 ### Progress Dialog
 A progress dialog shows a circular progress in an indeterminate state with or without a button

 ```kotlin
 QuickDialog(
                context = context,
                style = QuickDialogType.Progress,
                title = "Please wait",
                message = "Walking round the world")
                .show()
 ```

 This variant however shows a button so that a user can dismiss the dialog

 ```kotlin
QuickDialog(
                context = context,
                style = QuickDialogType.Progress,
                title = "Please wait",
                message = "Walking round the world")
                .overrideButtonNames("Hide Progress")
                .overrideClicks({ ->
                    Toast.makeText(context, "Clicked on Hide Progress", Toast.LENGTH_SHORT).show()
                }).showPositiveButton()
                .show()
 ```

### Alert Dialog
An alert dialog is used in situations when a user has to make a decision

```kotlin
QuickDialog(
                context = context,
                style = QuickDialogType.Alert,
                title = "Proceed",
                message = "Do you want to take this action")
                .overrideButtonNames("Yes", "No")
                .overrideClicks(positiveClick = { ->
                    Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show()
                }, negativeClick = { ->
                    Toast.makeText(context, "No", Toast.LENGTH_SHORT).show()
                })
                .show()
 ```

 The overrideClicks appears in three forms

 #### OverrideClicks #1
 ```kotlin
 fun overrideClicks(
            positiveClick: () -> Unit = {},
            negativeClick: () -> Unit = {},
            neutralClick: () -> Unit = {}
    )
 ```

 #### OverrideClicks #2
 ```kotlin
  fun overrideClicks(
            positiveClick: (dismiss: () -> Unit) -> Unit = { d -> },
            negativeClick: (dismiss: () -> Unit) -> Unit = { d -> },
            neutralClick: (dismiss: () -> Unit) -> Unit = { d -> }
    )
 ```
 The variable __d__ is an anonymos function that is passed from the implemetation
 of the overideClicks function. It is the __dismiss__ function in QuickDialog and it helps you dismiss the dialog in the click closure. All overloaded methods with __d__ supplied do not dismiss automatically.

 Lets see an example
 ```kotlin
 QuickDialog(
                context = context,
                style = QuickDialogType.Alert,
                title = "Proceed",
                message = "Do you want to take this action")
                .overrideButtonNames("Yes", "No")
                .overrideClicks(positiveClick = { dismiss ->
                    if (true) {
                        Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                }, negativeClick = { dismiss ->
                    if (true) {
                        Toast.makeText(context, "No", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                })
                .show()
 ```

 If we dont invoke __dismiss__ the Quick dialog wont disappear.

 #### OverrideClicks #3
 ```kotlin
 fun overrideClicks(
            positiveClick: (dismiss: () -> Unit, inputText: String) -> Unit = { d, s -> },
            negativeClick: (dismiss: () -> Unit, inputText: String) -> Unit = { d, s -> },
            neutralClick: (dismiss: () -> Unit, inputText: String) -> Unit = { d, s -> }
    )
 ```

 The __d__ variable is same as the one described above. However the __s__ is text that a user entered in the __WithInput__ variation of the Quick dialog

 lets see an example
 ```kotlin
 QuickDialog(
                context = context,
                style = QuickDialogType.WithInput,
                title = "Verify Code",
                message = "Please verify the SMS code that was sent to you")
                .overrideButtonNames("Verify", "Cancel", "Re-send")
                .overrideClicks(positiveClick = { dismiss, inputText ->
                    if (inputText.length < 3) {
                        Toast.makeText(context, "Please enter a 4 digit code", Toast.LENGTH_SHORT).show()
                    } else if (inputText == "4000") {
                        Toast.makeText(context, "Verified", Toast.LENGTH_SHORT).show()
                        dismiss()
                    } else {
                        Toast.makeText(context, "You entered the wrong code", Toast.LENGTH_SHORT).show()
                    }
                }, negativeClick = { dismiss, inputText ->
                    dismiss()
                }, neutralClick = { dismiss, inputText ->
                    //Your action
                    dismiss()
                })
                .withInputHint("Code")
                .withInputLength(4)
                .withInputType(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)
                .showNeutralButton()
                .show()
 ```

