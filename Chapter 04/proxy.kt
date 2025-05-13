class Image {
    // Fields and method to handle image data
}

// Interface that defines the methods to load the image
interface ImageLoader {
    fun loadImage(url: String): Image
}

// Real implementation that handles the loading of images from the remote server
class RealImageLoader : ImageLoader {
    override fun loadImage(url: String): Image {
        println("Loading of the image from the remote server: $url")
        return Image()
    }
}

// Proxy that manages image caching in order to optimize loading
class ImageLoaderProxy : ImageLoader {
    private val realImageLoader = RealImageLoader()
    private val imageCache = mutableMapOf<String, Image>()

    override fun loadImage(url: String): Image {
        // Check if the image is already in the cache
        return if (imageCache.containsKey(url)) {
            println("Image loaded from cache: $url")
            imageCache[url]!!
        } else {
            // If the image is not in the cache, loads it from the remote server and stores it
            val bitmap = realImageLoader.loadImage(url)
            imageCache[url] = bitmap
            bitmap
        }
    }
}

// Classe client che interagisce con il proxy per caricare le immagini
class ImageViewModel {
    private val imageLoader: ImageLoader = ImageLoaderProxy()

    fun displayImage(url: String) {
        val image = imageLoader.loadImage(url)
    }
}

fun main() {
    val imageViewModel = ImageViewModel()
    // First request: the image is loaded from the server and stored in the cache
    imageViewModel.displayImage("https://example.com/image1.png")
    // Second request for the same image: it will be loaded from cache
    imageViewModel.displayImage("https://example.com/image1.png")
}

// @Test
fun testImageCaching() {
    val imageLoader = ImageLoaderProxy()
    // First request: the image should be loaded from the server
    val image1 = imageLoader.loadImage("https://example.com/image1.png")
    // Second request: the image should be loaded from cache
    val image2 = imageLoader.loadImage("https://example.com/image1.png")
    // Verifies that the image is the same
    assert(image1 == image2)
}
