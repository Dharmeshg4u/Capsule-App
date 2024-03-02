package com.memoneet.capsuleapp.data.repository

import com.memoneet.capsuleapp.R
import com.memoneet.capsuleapp.data.model.Note
import com.memoneet.capsuleapp.data.model.QuizQuestion
import com.memoneet.capsuleapp.data.model.Video

class MockCapsuleRepository : CapsuleRepository {
    override suspend fun getVideo(): Video
    {
        //video url
        return Video("android.resource://com.memoneet.capsuleapp/${R.raw.incredible_india}")
    }

    override suspend fun getNote(): Note
    {
        //fetch Notes
        return Note("India, a land of diversity and rich cultural heritage, is a vibrant and populous country located in South Asia. It is known for its ancient civilizations, diverse languages, religions, and traditions. With a history that dates back thousands of years, India has been home to various dynasties, empires, and civilizations, each leaving its mark on the country's landscape and culture.\n" +
                "\n" +
                "Geographically, India boasts diverse landscapes, ranging from the majestic Himalayas in the north to the vast plains of the Ganges River and the lush greenery of the Western Ghats in the south. It is also home to a rich variety of flora and fauna, with several national parks and wildlife sanctuaries preserving its natural treasures.\n" +
                "\n" +
                "India is renowned for its cultural heritage, with a plethora of UNESCO World Heritage Sites such as the iconic Taj Mahal, Jaipur's majestic forts and palaces, the ancient temples of Khajuraho, and the historic city of Hampi. The country's festivals, including Diwali, Holi, Eid, and Christmas, reflect its religious and cultural diversity, bringing people together in celebration.\n" +
                "\n" +
                "Moreover, India is known for its contributions to literature, art, music, and cuisine. It has produced world-renowned figures like Mahatma Gandhi, Rabindranath Tagore, and Swami Vivekananda, who have left an indelible mark on the world stage.\n" +
                "\n" +
                "Despite its rich heritage and cultural diversity, India also faces various challenges, including poverty, inequality, and environmental degradation. However, the country continues to make strides in economic development, technology, and innovation, positioning itself as a major player on the global stage.\n" +
                "\n" +
                "Overall, India's vibrant culture, diverse landscapes, and rich history make it a fascinating and enchanting destination, captivating the hearts and minds of people around the world.")
    }

    override suspend fun getQuizQuestions(): List<QuizQuestion> {
        //fetch Quiz Questions
        return listOf(
            QuizQuestion("What is the capital city of India?", listOf("Mumbai", "New Delhi", "Kolkata", "Bangalore"), 1),
            QuizQuestion("Which river is considered the holiest in Hinduism and is known as the 'Ganga' in India?", listOf("Yamuna", "Brahmaputra", "Indus", "Ganges"), 3),
            QuizQuestion("What is the national animal of India?", listOf("Lion", "Tiger", "Elephant", "Rhinoceros"), 1),
            QuizQuestion("Who was the first Prime Minister of India?", listOf("Jawaharlal Nehru", "Mahatma Gandhi", "Subhas Chandra Bose", "Indira Gandhi"), 0),
            QuizQuestion("Which famous monument in India is known as the 'Symbol of Love'?", listOf("Qutub Minar", "India Gate", "Hawa Mahal", "Taj Mahal"), 3),
            QuizQuestion("In which city would you find the iconic landmark, the Gateway of India?", listOf("Mumbai", "Chennai", "Kolkata", "Delhi"), 0),
            QuizQuestion("Which festival, also known as the Festival of Lights, is widely celebrated across India?", listOf("Holi", "Diwali", "Navratri", "Dussehra"), 1),
            QuizQuestion("What is the national currency of India?", listOf("Rupee", "Rupiah", "Ringgit", "Renminbi"), 0),
            QuizQuestion("Which Indian state is known as the 'Land of the Gods'?", listOf("Himachal Pradesh", "Uttarakhand", "Rajasthan", "Kerala"), 1),
            QuizQuestion("Who was the leader of the Indian independence movement and is often referred to as the 'Father of the Nation'?", listOf("Bhagat Singh", "Jawaharlal Nehru", "Subhas Chandra Bose", "Mahatma Gandhi"), 3)
        )
    }
}