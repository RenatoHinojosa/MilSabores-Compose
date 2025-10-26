package com.example.milsaborescompose.data.local

object DataInitializer {
    fun getInitialProducts(): List<Product> {
        return listOf(
            // ========== TORTAS CIRCULARES (10 productos) ==========
            Product(
                name = "Torta Circular de Vainilla",
                description = "Bizcocho de vainilla clásico relleno con crema pastelera y cubierto con un glaseado dulce, perfecto para cualquier ocasión.",
                price = 25000.0,
                image = "https://www.aki.com.ec/wp-content/uploads/2021/01/torta-vainilla-foto.jpg",
                category = "circular"
            ),
            Product(
                name = "Torta Circular de Manjar",
                description = "Torta tradicional chilena con manjar y nueces, un deleite para los amantes de los sabores dulces y clásicos.",
                price = 42000.0,
                image = "https://cdn0.recetasgratis.net/es/posts/8/0/2/torta_milhojas_24208_orig.jpg",
                category = "circular"
            ),
            Product(
                name = "Torta Circular Red Velvet",
                description = "Icónica torta de terciopelo rojo con frosting de queso crema, irresistible para los amantes del chocolate suave.",
                price = 38000.0,
                image = "https://images.unsplash.com/photo-1586985289688-ca3cf47d3e6e?w=400&h=300&fit=crop",
                category = "circular"
            ),
            Product(
                name = "Torta Circular de Chocolate",
                description = "Intenso bizcocho de chocolate con ganache de chocolate belga, perfecta para los chocoadictos.",
                price = 36000.0,
                image = "https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=400&h=300&fit=crop",
                category = "circular"
            ),
            Product(
                name = "Torta Circular de Tres Leches",
                description = "Clásica torta empapada en tres tipos de leche, coronada con merengue italiano y canela.",
                price = 32000.0,
                image = "https://images.unsplash.com/photo-1621303837174-89787a7d4729?w=400&h=300&fit=crop",
                category = "circular"
            ),
            Product(
                name = "Torta Circular de Lúcuma",
                description = "Sabor tradicional chileno con crema de lúcuma y bizcocho esponjoso, un verdadero tesoro nacional.",
                price = 35000.0,
                image = "https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=400&h=300&fit=crop",
                category = "circular"
            ),
            Product(
                name = "Torta Circular de Zanahoria",
                description = "Húmeda torta de zanahoria con nueces y especias, cubierta con frosting de queso crema.",
                price = 34000.0,
                image = "https://images.unsplash.com/photo-1621303837174-89787a7d4729?w=400&h=300&fit=crop",
                category = "circular"
            ),
            Product(
                name = "Torta Circular de Frambuesa",
                description = "Delicada torta con crema de frambuesa y frutas frescas del bosque.",
                price = 40000.0,
                image = "https://images.unsplash.com/photo-1588195538326-c5b1e5b80857?w=400&h=300&fit=crop",
                category = "circular"
            ),
            Product(
                name = "Torta Circular de Limón",
                description = "Refrescante torta de limón con crema de limón y merengue italiano tostado.",
                price = 28000.0,
                image = "https://images.unsplash.com/photo-1519915212116-7cfef71f1d3e?w=400&h=300&fit=crop",
                category = "circular"
            ),
            Product(
                name = "Torta Circular Selva Negra",
                description = "Clásica torta alemana con chocolate, cerezas y crema chantilly, decorada con virutas de chocolate.",
                price = 40000.0,
                image = "https://images.unsplash.com/photo-1606313564200-e75d5e30476c?w=400&h=300&fit=crop",
                category = "circular"
            ),

            // ========== TORTAS CUADRADAS (10 productos) ==========
            Product(
                name = "Torta Cuadrada de Chocolate",
                description = "Rica torta de chocolate con cobertura de ganache y decoración elegante.",
                price = 45000.0,
                image = "https://cocinerosargentinos.com/content/recipes/original/recipes.20871.jpg",
                category = "cuadrada"
            ),
            Product(
                name = "Torta Cuadrada de Frutilla",
                description = "Torta esponjosa con crema y frutillas frescas, ideal para el verano.",
                price = 48000.0,
                image = "https://images.unsplash.com/photo-1565958011703-44f9829ba187?w=400&h=300&fit=crop",
                category = "cuadrada"
            ),
            Product(
                name = "Torta Cuadrada de Nuez",
                description = "Torta con nueces trituradas y crema de mantequilla de nuez, sabor intenso y único.",
                price = 47000.0,
                image = "https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=400&h=300&fit=crop",
                category = "cuadrada"
            ),
            Product(
                name = "Torta Cuadrada de Manjar y Coco",
                description = "Exquisita combinación de manjar con coco rallado y crema de mantequilla.",
                price = 49000.0,
                image = "https://images.unsplash.com/photo-1464349095431-e9a21285b5f3?w=400&h=300&fit=crop",
                category = "cuadrada"
            ),
            Product(
                name = "Torta Cuadrada Oreo",
                description = "Torta de chocolate con galletas Oreo trituradas y crema de queso con Oreo.",
                price = 50000.0,
                image = "https://images.unsplash.com/photo-1558961363-fa8fdf82db35?w=400&h=300&fit=crop",
                category = "cuadrada"
            ),
            Product(
                name = "Torta Cuadrada Red Velvet",
                description = "Elegante torta red velvet con múltiples capas y frosting de queso crema.",
                price = 52000.0,
                image = "https://images.unsplash.com/photo-1586985289688-ca3cf47d3e6e?w=400&h=300&fit=crop",
                category = "cuadrada"
            ),
            Product(
                name = "Torta Cuadrada de Naranja",
                description = "Refrescante torta de naranja con crema de mantequilla cítrica.",
                price = 46000.0,
                image = "https://images.unsplash.com/photo-1565958011703-44f9829ba187?w=400&h=300&fit=crop",
                category = "cuadrada"
            ),
            Product(
                name = "Torta Cuadrada Tiramisu",
                description = "Versión en torta del clásico italiano con capas de café, mascarpone y cacao.",
                price = 53000.0,
                image = "https://images.unsplash.com/photo-1571877227200-a0d98ea607e9?w=400&h=300&fit=crop",
                category = "cuadrada"
            ),
            Product(
                name = "Torta Cuadrada de Frutos del Bosque",
                description = "Torta con mezcla de arándanos, frambuesas y moras frescas con crema chantilly.",
                price = 51000.0,
                image = "https://images.unsplash.com/photo-1588195538326-c5b1e5b80857?w=400&h=300&fit=crop",
                category = "cuadrada"
            ),
            Product(
                name = "Torta Cuadrada de Café",
                description = "Torta con sabor a café expreso y cobertura de buttercream de café.",
                price = 48000.0,
                image = "https://images.unsplash.com/photo-1571877227200-a0d98ea607e9?w=400&h=300&fit=crop",
                category = "cuadrada"
            ),

            // ========== POSTRES INDIVIDUALES (10 productos) ==========
            Product(
                name = "Cheesecake Individual",
                description = "Cremoso cheesecake con base de galleta y topping de frutos rojos.",
                price = 6500.0,
                image = "https://images.unsplash.com/photo-1533134486753-c833f0ed4866?w=400&h=300&fit=crop",
                category = "individual"
            ),
            Product(
                name = "Brownie Individual",
                description = "Brownie de chocolate intenso, servido caliente con helado opcional.",
                price = 4500.0,
                image = "https://images.unsplash.com/photo-1607920591413-4ec007e70023?w=400&h=300&fit=crop",
                category = "individual"
            ),
            Product(
                name = "Macaron Individual",
                description = "Delicado macaron francés en variados sabores.",
                price = 2500.0,
                image = "https://images.unsplash.com/photo-1558312657-b2dead0febae?w=400&h=300&fit=crop",
                category = "individual"
            ),
            Product(
                name = "Éclair de Chocolate",
                description = "Clásico éclair francés relleno de crema pastelera y cubierto con chocolate.",
                price = 5000.0,
                image = "https://images.unsplash.com/photo-1612201142855-0a38cb0e7531?w=400&h=300&fit=crop",
                category = "individual"
            ),
            Product(
                name = "Tiramisú Individual",
                description = "Clásico postre italiano con café, mascarpone y cacao.",
                price = 7000.0,
                image = "https://images.unsplash.com/photo-1571877227200-a0d98ea607e9?w=400&h=300&fit=crop",
                category = "individual"
            ),
            Product(
                name = "Cupcake Gourmet",
                description = "Cupcake decorado con buttercream y toppings premium.",
                price = 3500.0,
                image = "https://images.unsplash.com/photo-1426869884541-df7117556757?w=400&h=300&fit=crop",
                category = "individual"
            ),
            Product(
                name = "Mousse de Chocolate",
                description = "Suave mousse de chocolate belga con crema chantilly.",
                price = 5500.0,
                image = "https://images.unsplash.com/photo-1541599540903-216a46ab7f9e?w=400&h=300&fit=crop",
                category = "individual"
            ),
            Product(
                name = "Tartaleta de Limón",
                description = "Delicada tartaleta con crema de limón y merengue tostado.",
                price = 5500.0,
                image = "https://images.unsplash.com/photo-1519915212116-7cfef71f1d3e?w=400&h=300&fit=crop",
                category = "individual"
            ),
            Product(
                name = "Crème Brûlée",
                description = "Postre francés con crema de vainilla y costra de azúcar caramelizada.",
                price = 7500.0,
                image = "https://images.unsplash.com/photo-1470124182917-cc6e71b22ecc?w=400&h=300&fit=crop",
                category = "individual"
            ),
            Product(
                name = "Mini Pavlova",
                description = "Merengue crujiente con crema chantilly y frutas tropicales frescas.",
                price = 6800.0,
                image = "https://images.unsplash.com/photo-1565958011703-44f9829ba187?w=400&h=300&fit=crop",
                category = "individual"
            ),

            // ========== PRODUCTOS SIN AZÚCAR (10 productos) ==========
            Product(
                name = "Torta Sin Azúcar de Chocolate",
                description = "Torta de chocolate endulzada con stevia, sin comprometer el sabor intenso del cacao.",
                price = 35000.0,
                image = "https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=400&h=300&fit=crop",
                category = "sinazucar"
            ),
            Product(
                name = "Torta Sin Azúcar de Zanahoria",
                description = "Saludable torta de zanahoria con nueces y frosting sin azúcar de queso crema.",
                price = 33000.0,
                image = "https://images.unsplash.com/photo-1621303837174-89787a7d4729?w=400&h=300&fit=crop",
                category = "sinazucar"
            ),
            Product(
                name = "Brownies Sin Azúcar",
                description = "Pack de 6 brownies de chocolate intenso endulzados con eritritol.",
                price = 15000.0,
                image = "https://images.unsplash.com/photo-1607920591413-4ec007e70023?w=400&h=300&fit=crop",
                category = "sinazucar"
            ),
            Product(
                name = "Cheesecake Sin Azúcar",
                description = "Cremoso cheesecake endulzado naturalmente con frutos rojos frescos.",
                price = 32000.0,
                image = "https://images.unsplash.com/photo-1533134486753-c833f0ed4866?w=400&h=300&fit=crop",
                category = "sinazucar"
            ),
            Product(
                name = "Muffins Sin Azúcar",
                description = "Set de 6 muffins con arándanos, endulzados con dátiles.",
                price = 12000.0,
                image = "https://images.unsplash.com/photo-1426869884541-df7117556757?w=400&h=300&fit=crop",
                category = "sinazucar"
            ),
            Product(
                name = "Torta Sin Azúcar de Limón",
                description = "Refrescante torta de limón con edulcorante natural de stevia.",
                price = 30000.0,
                image = "https://images.unsplash.com/photo-1519915212116-7cfef71f1d3e?w=400&h=300&fit=crop",
                category = "sinazucar"
            ),
            Product(
                name = "Galletas Sin Azúcar Mix",
                description = "Caja con 12 galletas variadas sin azúcar: avena, chocolate y coco.",
                price = 10000.0,
                image = "https://images.unsplash.com/photo-1499636136210-6f4ee915583e?w=400&h=300&fit=crop",
                category = "sinazucar"
            ),
            Product(
                name = "Pie Sin Azúcar de Manzana",
                description = "Pie de manzana con canela, endulzado naturalmente con manzanas y stevia.",
                price = 28000.0,
                image = "https://images.unsplash.com/photo-1535920527002-b35e96722eb9?w=400&h=300&fit=crop",
                category = "sinazucar"
            ),
            Product(
                name = "Mousse Sin Azúcar de Frambuesa",
                description = "Ligero mousse de frambuesa sin azúcar añadida.",
                price = 18000.0,
                image = "https://images.unsplash.com/photo-1541599540903-216a46ab7f9e?w=400&h=300&fit=crop",
                category = "sinazucar"
            ),
            Product(
                name = "Torta Sin Azúcar Red Velvet",
                description = "Versión saludable de la clásica red velvet con eritritol.",
                price = 38000.0,
                image = "https://images.unsplash.com/photo-1586985289688-ca3cf47d3e6e?w=400&h=300&fit=crop",
                category = "sinazucar"
            ),

            // ========== PASTELERÍA TRADICIONAL (10 productos) ==========
            Product(
                name = "Mil Hojas Tradicional",
                description = "Clásico mil hojas chileno con manjar y merengue italiano.",
                price = 30000.0,
                image = "https://cdn0.recetasgratis.net/es/posts/8/0/2/torta_milhojas_24208_orig.jpg",
                category = "tradicional"
            ),
            Product(
                name = "Brazo de Reina",
                description = "Tradicional brazo de reina relleno con manjar o crema pastelera.",
                price = 25000.0,
                image = "https://images.unsplash.com/photo-1586985289688-ca3cf47d3e6e?w=400&h=300&fit=crop",
                category = "tradicional"
            ),
            Product(
                name = "Sopaipillas Pasadas",
                description = "12 sopaipillas tradicionales bañadas en miel de chancaca.",
                price = 8000.0,
                image = "https://www.tipicochileno.cl/wp-content/uploads/2025/06/News_Gato_NuevoDiseno-junio_c-SOPAIPILLAS.png",
                category = "tradicional"
            ),
            Product(
                name = "Empanadas de Pino Dulces",
                description = "6 empanadas dulces con relleno de pino caramelizado.",
                price = 9000.0,
                image = "https://www.gourmet.cl/wp-content/uploads/2022/12/torta-de-panqueque-naranja.jpg",
                category = "tradicional"
            ),
            Product(
                name = "Calzones Rotos",
                description = "Docena de calzones rotos tradicionales espolvoreados con azúcar flor.",
                price = 7000.0,
                image = "https://www.gourmet.cl/wp-content/uploads/2022/12/torta-de-panqueque-naranja.jpg",
                category = "tradicional"
            ),
            Product(
                name = "Alfajores Chilenos",
                description = "6 alfajores artesanales con manjar y coco rallado.",
                price = 10000.0,
                image = "https://www.gourmet.cl/wp-content/uploads/2022/12/torta-de-panqueque-naranja.jpg",
                category = "tradicional"
            ),
            Product(
                name = "Berlinas Tradicionales",
                description = "6 berlinas rellenas con crema pastelera.",
                price = 8500.0,
                image = "https://www.gourmet.cl/wp-content/uploads/2022/12/torta-de-panqueque-naranja.jpg",
                category = "tradicional"
            ),
            Product(
                name = "Churros con Manjar",
                description = "12 churros crujientes con manjar para untar.",
                price = 9500.0,
                image = "https://www.gourmet.cl/wp-content/uploads/2022/12/torta-de-panqueque-naranja.jpg",
                category = "tradicional"
            ),
            Product(
                name = "Pan de Pascua Artesanal",
                description = "Tradicional pan de pascua con frutas confitadas y nueces.",
                price = 15000.0,
                image = "https://www.gourmet.cl/wp-content/uploads/2022/12/torta-de-panqueque-naranja.jpg",
                category = "tradicional"
            ),
            Product(
                name = "Kuchen de Manzana",
                description = "Kuchen alemán-chileno con manzanas caramelizadas.",
                price = 22000.0,
                image = "https://www.gourmet.cl/wp-content/uploads/2022/12/torta-de-panqueque-naranja.jpg",
                category = "tradicional"
            ),

            // ========== PRODUCTOS SIN GLUTEN (10 productos) ==========
            Product(
                name = "Torta Sin Gluten de Chocolate",
                description = "Deliciosa torta de chocolate elaborada con harina de almendras, completamente sin gluten.",
                price = 42000.0,
                image = "https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=400&h=300&fit=crop",
                category = "singluten"
            ),
            Product(
                name = "Torta Sin Gluten de Zanahoria",
                description = "Húmeda torta de zanahoria sin gluten con nueces y frosting de queso crema.",
                price = 40000.0,
                image = "https://images.unsplash.com/photo-1621303837174-89787a7d4729?w=400&h=300&fit=crop",
                category = "singluten"
            ),
            Product(
                name = "Brownies Sin Gluten",
                description = "Pack de 6 brownies intensos de chocolate sin gluten.",
                price = 16000.0,
                image = "https://images.unsplash.com/photo-1607920591413-4ec007e70023?w=400&h=300&fit=crop",
                category = "singluten"
            ),
            Product(
                name = "Muffins Sin Gluten de Arándanos",
                description = "Set de 6 muffins esponjosos con arándanos frescos, sin gluten.",
                price = 14000.0,
                image = "https://chyfoodservice.cl/wp-content/uploads/2020/03/muffins-arandanos-singluten.jpg",
                category = "singluten"
            ),
            Product(
                name = "Cheesecake Sin Gluten",
                description = "Cremoso cheesecake con base sin gluten y topping de frutos rojos.",
                price = 35000.0,
                image = "https://images.unsplash.com/photo-1533134486753-c833f0ed4866?w=400&h=300&fit=crop",
                category = "singluten"
            ),
            Product(
                name = "Torta Sin Gluten de Limón",
                description = "Refrescante torta de limón con harina de almendras, completamente sin gluten.",
                price = 38000.0,
                image = "https://images.unsplash.com/photo-1519915212116-7cfef71f1d3e?w=400&h=300&fit=crop",
                category = "singluten"
            ),
            Product(
                name = "Galletas Sin Gluten Variadas",
                description = "Caja de 12 galletas sin gluten: chocolate chip, avena y coco.",
                price = 11000.0,
                image = "https://images.unsplash.com/photo-1499636136210-6f4ee915583e?w=400&h=300&fit=crop",
                category = "singluten"
            ),
            Product(
                name = "Cupcakes Sin Gluten",
                description = "Set de 6 cupcakes decorados, elaborados con ingredientes sin gluten.",
                price = 18000.0,
                image = "https://images.unsplash.com/photo-1426869884541-df7117556757?w=400&h=300&fit=crop",
                category = "singluten"
            ),
            Product(
                name = "Alfajores Sin Gluten",
                description = "6 alfajores sin gluten rellenos con manjar premium.",
                price = 13000.0,
                image = "https://chyfoodservice.cl/wp-content/uploads/2020/03/muffins-arandanos-singluten.jpg",
                category = "singluten"
            ),
            Product(
                name = "Pan Sin Gluten Artesanal",
                description = "Pan artesanal sin gluten perfecto para acompañar.",
                price = 8000.0,
                image = "https://chyfoodservice.cl/wp-content/uploads/2020/03/muffins-arandanos-singluten.jpg",
                category = "singluten"
            ),

            // ========== PRODUCTOS VEGANOS (10 productos) ==========
            Product(
                name = "Torta Vegana de Chocolate",
                description = "Torta 100% vegana con cacao puro y leche de almendras, sorprendentemente deliciosa.",
                price = 38000.0,
                image = "https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=400&h=300&fit=crop",
                category = "vegano"
            ),
            Product(
                name = "Torta Vegana de Zanahoria",
                description = "Torta de zanahoria vegana con nueces y frosting de anacardos.",
                price = 36000.0,
                image = "https://images.unsplash.com/photo-1621303837174-89787a7d4729?w=400&h=300&fit=crop",
                category = "vegano"
            ),
            Product(
                name = "Brownies Veganos",
                description = "Pack de 6 brownies veganos intensos sin productos de origen animal.",
                price = 14000.0,
                image = "https://images.unsplash.com/photo-1607920591413-4ec007e70023?w=400&h=300&fit=crop",
                category = "vegano"
            ),
            Product(
                name = "Muffins Veganos de Plátano",
                description = "Set de 6 muffins veganos con plátano y nueces.",
                price = 12000.0,
                image = "https://images.unsplash.com/photo-1426869884541-df7117556757?w=400&h=300&fit=crop",
                category = "vegano"
            ),
            Product(
                name = "Cheesecake Vegano",
                description = "Cheesecake vegano a base de anacardos con frutos del bosque.",
                price = 34000.0,
                image = "https://i.pinimg.com/736x/c0/7e/4e/c07e4e39847785119f1f0f91ac116c63.jpg",
                category = "vegano"
            ),
            Product(
                name = "Galletas Veganas Mix",
                description = "Caja de 12 galletas veganas variadas: chocochip, avena y coco.",
                price = 10000.0,
                image = "https://images.unsplash.com/photo-1499636136210-6f4ee915583e?w=400&h=300&fit=crop",
                category = "vegano"
            ),
            Product(
                name = "Torta Vegana de Limón",
                description = "Refrescante torta vegana de limón con glaseado de limón.",
                price = 32000.0,
                image = "https://images.unsplash.com/photo-1519915212116-7cfef71f1d3e?w=400&h=300&fit=crop",
                category = "vegano"
            ),
            Product(
                name = "Cupcakes Veganos",
                description = "Set de 6 cupcakes veganos decorados con buttercream vegano.",
                price = 16000.0,
                image = "https://images.unsplash.com/photo-1426869884541-df7117556757?w=400&h=300&fit=crop",
                category = "vegano"
            ),
            Product(
                name = "Alfajores Veganos",
                description = "6 alfajores veganos rellenos con manjar vegano.",
                price = 11000.0,
                image = "https://i.pinimg.com/736x/c0/7e/4e/c07e4e39847785119f1f0f91ac116c63.jpg",
                category = "vegano"
            ),
            Product(
                name = "Pie Vegano de Manzana",
                description = "Pie de manzana con masa vegana y relleno de manzanas especiadas.",
                price = 30000.0,
                image = "https://images.unsplash.com/photo-1535920527002-b35e96722eb9?w=400&h=300&fit=crop",
                category = "vegano"
            ),

            // ========== TORTAS ESPECIALES (10 productos) ==========
            Product(
                name = "Torta de Tres Chocolates",
                description = "Elegante torta con capas de chocolate blanco, con leche y oscuro.",
                price = 55000.0,
                image = "https://images.unsplash.com/photo-1606890737304-57a1ca8a5b62?w=400&h=300&fit=crop",
                category = "especial"
            ),
            Product(
                name = "Torta Ópera",
                description = "Sofisticada torta francesa con capas de café, ganache y crema de mantequilla.",
                price = 58000.0,
                image = "https://images.unsplash.com/photo-1571877227200-a0d98ea607e9?w=400&h=300&fit=crop",
                category = "especial"
            ),
            Product(
                name = "Torta Sacher Vienesa",
                description = "Clásica torta austriaca de chocolate con mermelada de damasco y chocolate.",
                price = 52000.0,
                image = "https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=400&h=300&fit=crop",
                category = "especial"
            ),
            Product(
                name = "Torta Personalizada",
                description = "Diseña tu torta soñada con nuestros maestros pasteleros.",
                price = 75000.0,
                image = "https://www.sorprendelima.pe/cdn/shop/files/IMG_4787.jpg?v=1709231148",
                category = "especial"
            ),
            Product(
                name = "Torta de Maracuyá Premium",
                description = "Exquisita torta tropical con crema de maracuyá y merengue suizo.",
                price = 54000.0,
                image = "https://images.unsplash.com/photo-1565958011703-44f9829ba187?w=400&h=300&fit=crop",
                category = "especial"
            ),
            Product(
                name = "Torta Saint Honoré",
                description = "Clásico francés con profiteroles rellenos y crema chantilly.",
                price = 62000.0,
                image = "https://images.unsplash.com/photo-1464349095431-e9a21285b5f3?w=400&h=300&fit=crop",
                category = "especial"
            ),
            Product(
                name = "Torta Black Forest Premium",
                description = "Versión premium de la Selva Negra con cerezas importadas.",
                price = 59000.0,
                image = "https://images.unsplash.com/photo-1606313564200-e75d5e30476c?w=400&h=300&fit=crop",
                category = "especial"
            ),
            Product(
                name = "Torta de Té Matcha",
                description = "Innovadora torta con té matcha japonés y crema de vainilla.",
                price = 57000.0,
                image = "https://images.unsplash.com/photo-1571877227200-a0d98ea607e9?w=400&h=300&fit=crop",
                category = "especial"
            ),
            Product(
                name = "Torta de Dulce de Leche Argentino",
                description = "Torta con auténtico dulce de leche argentino y almendras tostadas.",
                price = 53000.0,
                image = "https://images.unsplash.com/photo-1464349095431-e9a21285b5f3?w=400&h=300&fit=crop",
                category = "especial"
            ),
            Product(
                name = "Torta de Frambuesa y Pistacho",
                description = "Elegante combinación de frambuesa fresca con crema de pistacho.",
                price = 56000.0,
                image = "https://images.unsplash.com/photo-1588195538326-c5b1e5b80857?w=400&h=300&fit=crop",
                category = "especial"
            )
        )
    }
}
