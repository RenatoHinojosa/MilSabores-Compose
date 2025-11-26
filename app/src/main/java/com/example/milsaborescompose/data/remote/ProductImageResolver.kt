package com.example.milsaborescompose.data.remote

object ProductImageResolver {
    private const val BASE_URL = "http://pasteleriamilsabores-melipilla.s3-website-us-east-1.amazonaws.com/datasets-tortas/"
    private const val DEFAULT_IMAGE = "https://www.gourmet.cl/wp-content/uploads/2022/12/torta-de-panqueque-naranja.jpg"

    private val productIdToImageName = mapOf(
        1L to "001.jpg", 2L to "002.jpg", 3L to "003.jpg", 4L to "004.jpg", 5L to "005.jpg",
        6L to "006.jpg", 7L to "008.jpg", 8L to "009.jpg", 9L to "010.jpg", 10L to "011.jpg",
        11L to "014.jpg", 12L to "015.jpg", 13L to "017.jpg", 14L to "018.jpg", 15L to "019.jpg",
        16L to "020.jpg", 17L to "021.jpg", 18L to "022.jpg", 19L to "023.jpg", 20L to "024.jpg",
        21L to "025.jpg", 22L to "029.jpg", 23L to "030.jpg", 24L to "031.jpg", 25L to "036.jpg",
        26L to "039.jpg", 27L to "042.jpg", 28L to "043.jpg", 29L to "047.jpg", 30L to "054.jpg",
        31L to "056.jpg", 32L to "057.jpg", 33L to "060.jpg", 34L to "061.jpg", 35L to "069.jpg",
        36L to "071.jpg", 37L to "072.jpg", 38L to "073.jpg", 39L to "076.jpg", 40L to "082.jpg",
        41L to "085.jpg", 42L to "092.jpg", 43L to "097.jpg", 44L to "101.jpg", 45L to "102.jpg",
        46L to "103.jpg", 47L to "104.jpg", 48L to "105.jpg", 49L to "106.jpg", 50L to "107.jpg",
        51L to "117.jpg", 52L to "166.jpg", 53L to "167.jpg", 54L to "168.jpg", 55L to "169.jpg",
        56L to "170.jpg", 57L to "171.jpg", 58L to "172.jpg", 59L to "173.jpg", 60L to "174.jpg",
        61L to "176.jpg", 62L to "177.jpg", 63L to "178.jpg", 64L to "179.jpg", 65L to "180.jpg",
        66L to "181.jpg", 67L to "182.jpg", 68L to "183.jpg", 69L to "184.jpg", 70L to "185.jpg",
        71L to "186.jpg", 72L to "187.jpg", 73L to "188.jpg", 74L to "189.jpg", 75L to "190.jpg",
        76L to "191.jpg", 77L to "192.jpg", 78L to "193.jpg", 79L to "194.jpg", 80L to "196.jpg"
    )

    fun getImageUrlForProductId(productId: Long): String {
        val imageName = productIdToImageName[productId]
        return if (imageName != null) {
            "$BASE_URL$imageName"
        } else {
            DEFAULT_IMAGE
        }
    }
}
