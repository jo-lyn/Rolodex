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
