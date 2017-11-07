# jo-lyn
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void removeTag(Tag tag) throws DuplicatePersonException, PersonNotFoundException {
        for (ReadOnlyPerson person: rolodex.getPersonList()) {
            Person newPerson = new Person(person);

            Set<Tag> newTags = new HashSet<>(newPerson.getTags());
            newTags.remove(tag);
            newPerson.setTags(newTags);

            rolodex.updatePerson(person, newPerson);
        }
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Returns the index of the given person
     */
    public Index getIndex(ReadOnlyPerson target) {
        return Index.fromZeroBased(sortedPersons.indexOf(target));
    }
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    public void setFocus() {
        commandTextField.requestFocus();
    }

    public boolean isFocused() {
        return commandTextField.isFocused();
    }

    /**
     * Load images for keyboard icons in the command box.
     */
    private void loadKeyboardIcons() {
        keyboardIdle = new Image(getClass().getResourceAsStream("/images/keyboard.png"));
        keyboardTyping = new Image(getClass().getResourceAsStream("/images/keyboardTyping.png"));
        keyboardError = new Image(getClass().getResourceAsStream("/images/keyboardError.png"));
    }
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    /**
     * Sets the command box style to use the default style.
     * {@code keyboardTyping} icon changes to {@code keyboardIdle} when there is no change
     * to text field after some time.
     */
    private void updateKeyboardIconAndStyle() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();
        keyboardIcon.setImage(keyboardTyping);
        pause.setOnFinished(event -> {
            if (!styleClass.contains(ERROR_STYLE_CLASS)) {
                keyboardIcon.setImage(keyboardIdle);
            }
        });
        pause.playFromStart();
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    /**
     * Sets the command box style to indicate a failed command.
     */
    public void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
        keyboardIcon.setImage(keyboardError);
    }
```
