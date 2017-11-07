# jo-lyn
###### \java\seedu\address\model\ModelManagerTest.java
``` java
    @Test
    public void removeTag() throws IllegalValueException, PersonNotFoundException {

        Set<Tag> tagSet1 = getTagSet("friends", "classmate");
        Set<Tag> tagSet2 = getTagSet("owesMoney", "classmate");

        Person person1 = new Person(ALICE);
        person1.setTags(tagSet1);

        Person person2 = new Person(BENSON);
        person2.setTags(tagSet2);

        Rolodex rolodex = new RolodexBuilder().withPerson(person1).withPerson(person2).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(rolodex, userPrefs);

        Tag tagToRemove = new Tag("classmate");
        modelManager.removeTag(tagToRemove);

        Set<Tag> tagSet1New = getTagSet("friends");
        Set<Tag> tagSet2New = getTagSet("owesMoney");

        Person person1New = new Person(ALICE);
        person1.setTags(tagSet1New);
        Person person2New = new Person(BENSON);
        person2.setTags(tagSet2New);

        // check that tagToRemove from all persons are removed
        assertTrue(person1.equals(person1New));
        assertTrue(person2.equals(person2New));

        // check that tagSets are not equal because both are null
        assertFalse(person1.getTags().equals(null));
        assertFalse(person2.getTags().equals(null));
    }

    @Test
    public void getIndex() {
        Rolodex rolodex = new RolodexBuilder().withPerson(ALICE).withPerson(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(rolodex, userPrefs);

        // Alice has first index
        Index expectedIndex = INDEX_FIRST_PERSON;
        Index actualIndex = modelManager.getIndex(ALICE);
        assertEquals(expectedIndex, actualIndex);

        // Benson has second index
        expectedIndex = INDEX_SECOND_PERSON;
        actualIndex = modelManager.getIndex(BENSON);
        assertEquals(expectedIndex, actualIndex);
    }
```
