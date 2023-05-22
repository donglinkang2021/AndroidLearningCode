package com.linkdom.readbook.data

import com.linkdom.readbook.model.Book
import com.linkdom.readbook.model.BookCategory
import com.linkdom.readbook.model.Chapter


object SampleData {
    val chapterSample =  List(100) { index ->
        Chapter().apply {
            name = "Chapter ${index + 1}"
        }
    }

    val TheWanderingEarthChapter = listOf(
        Chapter().apply {
            name = "第一章：刹车时代"
        },
        Chapter().apply {
            name = "第二章：逃逸时代"
        },
        Chapter().apply {
            name = "叛乱"
        },
        Chapter().apply {
            name = "第三章：流浪时代"
        },
    )

    val booksSample = listOf(
        Book().apply {
            id = "0"
            name = "The Hunger Games"
            author = "Suzanne Collins"
            cover = "https://tse4-mm.cn.bing.net/th/id/OIP-C.ZSJGEiAM4ilVrcAl4wiEAQHaK-?w=115&h=180&c=7&r=0&o=5&pid=1.7"
            description = "The Hunger Games can refer to a series of novels, " +
                    "a film series, or the first novel and film in the series²³. The Hunger Games is a dystopian story " +
                    "that follows Katniss Everdeen, a teenage girl who volunteers to take her sister's place in a deadly " +
                    "competition where 24 young people from 12 districts are forced to fight to the death on live television¹². " +
                    "The story explores themes of survival, rebellion, oppression, and love as Katniss tries to outwit the ruthless " +
                    "Capitol and protect her allies².\n"
            chapters = chapterSample
        },
        Book().apply {
            id = "1"
            name = "Harry Potter and the Sorcerer's Stone"
            author = "J.K. Rowling"
            cover = "https://tse3-mm.cn.bing.net/th/id/OIP-C.xZ3LnbYfTGsl_pNfU4I5PgHaKY?w=115&h=180&c=7&r=0&o=5&pid=1.7"
            description = "Harry Potter and the Sorcerer's Stone is the first novel in the Harry Potter series and J.K. Rowling's " +
                    "debut novel, first published in 1997 by Bloomsbury. It follows Harry Potter, a young wizard who discovers his magical " +
                    "heritage on his eleventh birthday, when he receives a letter of acceptance to Hogwarts School of Witchcraft and Wizardry. " +
                    "Harry makes close friends and a few enemies during his first year at the school, and with the help of his friends, " +
                    "Harry faces an attempted comeback by the dark wizard Lord Voldemort, who killed Harry's parents, but failed to kill Harry " +
                    "when he was just 15 months old.\n"
            chapters = chapterSample
        },
        Book().apply {
            id = "2"
            name = "To Kill a Mockingbird"
            author = "Harper Lee"
            cover = "https://tse3-mm.cn.bing.net/th/id/OIP-C.bvsOC5rMirlDAGp14NAgMwHaLH?w=120&h=180&c=7&r=0&o=5&pid=1.7"
            description = "To Kill a Mockingbird is a novel by Harper Lee published in 1960. It was immediately successful, " +
                    "winning the Pulitzer Prize, and has become a classic of modern American literature. The plot and characters " +
                    "are loosely based on Lee's observations of her family, her neighbors and an event that occurred near her hometown " +
                    "of Monroeville, Alabama, in 1936, when she was 10 years old. It is told from the point of view of Scout Finch, " +
                    "who is 6 years old at the start of the book. The story takes place in the fictional town of Maycomb, Alabama, " +
                    "and in surrounding rural areas, during three years of the Great Depression.\n"
            chapters = chapterSample
        },
        Book().apply {
            id = "3"
            name = "The Book Thief"
            author = "Markus Zusak"
            cover = "https://tse1-mm.cn.bing.net/th/id/OIP-C.aKYdpwm9xm4w6SAvg_FSsgHaK9?w=118&h=180&c=7&r=0&o=5&pid=1.7"
            description = "The Book Thief is a 2005 historical novel by Australian author Markus Zusak and is his most popular work. " +
                    "Published in 2005, the book won the 2006 Commonwealth Writers' Prize Best Book Award and was " +
                    "shortlisted for the 2006 Australian Book Industry Awards Book of the Year Award. It was adapted into a " +
                    "2013 film of the same name directed by Brian Percival.\n"
            chapters = chapterSample
        },
        Book().apply {
            id = "4"
            name = "Pride and Prejudice"
            author = "Jane Austen"
            cover = "https://tse1-mm.cn.bing.net/th/id/OIP-C.0M-waiYskseNSQXn-CDbZAHaJ4?w=126&h=180&c=7&r=0&o=5&pid=1.7"
            description = "Pride and Prejudice is an 1813 romantic novel of manners written by Jane Austen. " +
                    "The novel follows the character development of Elizabeth Bennet, the dynamic protagonist of the " +
                    "book who learns about the repercussions of hasty judgments and comes to appreciate the difference " +
                    "between superficial goodness and actual goodness. The comedy of the writing lies in the depiction of " +
                    "the manners and morals of the landed gentry of the British Regency.\n"
            chapters = chapterSample
        },
        Book().apply {
            id = "5"
            name = "The Hobbit"
            author = "J.R.R. Tolkien"
            cover = "https://tse4-mm.cn.bing.net/th/id/OIP-C.6rp_64loKflNQokWSD3ouwHaJ4?w=206&h=275&c=7&r=0&o=5&pid=1.7"
            description = "The Hobbit, or There and Back Again is a children's fantasy novel by English author J. R. R. Tolkien. " +
                    "It was published on 21 September 1937 to wide critical acclaim, being nominated for the Carnegie Medal " +
                    "and awarded a prize from the New York Herald Tribune for best juvenile fiction. The book remains popular " +
                    "and is recognized as a classic in children's literature.\n"
            chapters = chapterSample
        },
        Book().apply {
            id = "6"
            name = "流浪地球"
            author = "刘慈欣"
            cover = "https://tse4-mm.cn.bing.net/th/id/OIP-C.zIFuIOntcTdC_vq7bdPIAQHaMO?w=194&h=321&c=7&r=0&o=5&pid=1.7"
            description = "《流浪地球》是中国科幻小说作家刘慈欣的作品，2000年发表于《科幻世界》第7期。" +
                    "这本书收录了刘慈欣的十四篇短篇科幻小说，其中最著名的是《流浪地球》，讲述了人类为了逃离太阳即将毁灭的命运，" +
                    "用巨大的推进器将地球推出太阳系，向比邻星进发的壮阔故事。刘慈欣的小说充满了想象力和诗意，" +
                    "展现了从奇点到宇宙边际的所有尺度，跨越了从白垩纪到未来千年的漫长时光。"
            chapters = TheWanderingEarthChapter
        },
        Book().apply {
            id = "7"
            name = "三体"
            author = "刘慈欣"
            cover = "https://tse4-mm.cn.bing.net/th/id/OIP-C.ZqOJJbL0T39hbi-GIrRJZgHaK2?w=206&h=302&c=7&r=0&o=5&pid=1.7"
            description = "《三体》是中国大陆作家刘慈欣于2006年5月至12月在《科幻世界》杂志上连载的一部长篇科幻小說，" +
                    "出版后成为中国大陆最畅销的科幻长篇小说之一。2008年，该书的单行本由重庆出版社出版。" +
                    "本书是三体系列（系列原名为：地球往事三部曲。）的第一部，该系列的第二部《三体II：黑暗森林》已经于2008年5月出版。2010年11月，" +
                    "第三部《三体III：死神永生》出版发行。2011年，在台湾陆续出版。小说的英文版獲得美國科幻奇幻作家協會2014年度「星雲獎」提名，并荣获2015年雨果奖最佳小说奖。"
            chapters = chapterSample
        },
    )

    private val myBooks = listOf(
        Book().apply {
            id = "6"
            name = "流浪地球"
            author = "刘慈欣"
            cover = "https://tse4-mm.cn.bing.net/th/id/OIP-C.zIFuIOntcTdC_vq7bdPIAQHaMO?w=194&h=321&c=7&r=0&o=5&pid=1.7"
            description = "《流浪地球》是中国科幻小说作家刘慈欣的作品，2000年发表于《科幻世界》第7期。" +
                    "这本书收录了刘慈欣的十四篇短篇科幻小说，其中最著名的是《流浪地球》，讲述了人类为了逃离太阳即将毁灭的命运，" +
                    "用巨大的推进器将地球推出太阳系，向比邻星进发的壮阔故事。刘慈欣的小说充满了想象力和诗意，" +
                    "展现了从奇点到宇宙边际的所有尺度，跨越了从白垩纪到未来千年的漫长时光。"
            chapters = TheWanderingEarthChapter
        },
        Book().apply {
            id = "7"
            name = "三体"
            author = "刘慈欣"
            cover = "https://tse4-mm.cn.bing.net/th/id/OIP-C.ZqOJJbL0T39hbi-GIrRJZgHaK2?w=206&h=302&c=7&r=0&o=5&pid=1.7"
            description = "《三体》是中国大陆作家刘慈欣于2006年5月至12月在《科幻世界》杂志上连载的一部长篇科幻小說，" +
                    "出版后成为中国大陆最畅销的科幻长篇小说之一。2008年，该书的单行本由重庆出版社出版。" +
                    "本书是三体系列（系列原名为：地球往事三部曲。）的第一部，该系列的第二部《三体II：黑暗森林》已经于2008年5月出版。2010年11月，" +
                    "第三部《三体III：死神永生》出版发行。2011年，在台湾陆续出版。小说的英文版獲得美國科幻奇幻作家協會2014年度「星雲獎」提名，并荣获2015年雨果奖最佳小说奖。"
            chapters = chapterSample
        },
    )

    val bookCategories = listOf(
        BookCategory().apply {
            name = "My Books"
            books = myBooks
        },
        BookCategory().apply {
            name = "Popular"
            books = booksSample
        },
        BookCategory().apply {
            name = "New Releases"
            books = booksSample
        },
        BookCategory().apply {
            name = "Best Sellers"
            books = booksSample
        },
        BookCategory().apply {
            name = "Coming Soon"
            books = booksSample
        }
    )
}