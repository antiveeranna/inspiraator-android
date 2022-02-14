package com.masendav.inspiraator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InspirationViewModel() : ViewModel() {

    private var locations = listOf("")
    private var locationIndex = 0
    private var _location = MutableLiveData("")
    val location: LiveData<String> = _location

    private var emotions = listOf("")
    private var emotionIndex = 0
    private var _emotion = MutableLiveData("")
    var emotion: LiveData<String> = _emotion

    private var things = listOf("")
    private var thingIndex = 0
    private var _thing = MutableLiveData("")
    var thing: LiveData<String> = _thing

    private var relations = listOf("")
    private var relationIndex = 0

    private var _relation = MutableLiveData("")
    var relation: LiveData<String> = _relation

    private var characters = listOf("")
    private var characterIndex = 0

    private var attributes = listOf("")
    private var attributeIndex = 0

    private var _random2 = MutableLiveData("")
    var random2: LiveData<String> = _random2

    fun uc (input:String): String {
        return input[0].uppercaseChar() + input.substring(1)
    }

    fun loadData(input: Inspiration) {

        this.locations = input.locations.shuffled()
        this.emotions = input.emotions.shuffled();
        this.things = input.objects.shuffled()
        this.relations = input.relations.shuffled()
        this.characters = input.characters.shuffled()
        this.attributes = input.attributes.shuffled()

        populate()

    }

    fun populate () {
        _location.value = uc(locations.get(locationIndex))
        _emotion.value = uc(emotions.get(emotionIndex))
        _thing.value = uc(things.get(thingIndex))
        _relation.value = uc(relations.get(relationIndex))

        _random2.value = uc(nextAttribute()) + " " + nextCharacter() + " ja " + nextAttribute() + " " + nextCharacter()
    }

    fun nextCharacter():String {
        var character = characters.get(characterIndex);

        characterIndex += 1;
        if (characterIndex == characters.size) {
            characterIndex = 0;
        }

        return character;
    }

    fun nextAttribute():String {
        val attr = attributes.get(attributeIndex);

        attributeIndex += 1;
        if (attributeIndex == attributes.size) {
            attributeIndex = 0;
        }

        return attr;
    }


    fun onNewChoice() {
        Log.d("INSPX", "Another go");

        locationIndex += 1
        if (locationIndex == locations.size) {
            locationIndex = 0
        }

        emotionIndex += 1
        if (emotionIndex == emotions.size) {
            emotionIndex = 0
        }

        thingIndex += 1
        if (thingIndex == things.size) {
            thingIndex = 0
        }

        relationIndex += 1
        if (relationIndex == relations.size) {
            relationIndex = 0
        }



        populate()

    }

}
