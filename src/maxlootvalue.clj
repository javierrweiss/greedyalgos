(ns GreedyAlgorithms.maxlootvalue)

(defn order-by-value
  [coll]
  (let [value-weight (for [[a b] coll] {:value a :weight b})]
    (vec (sort-by :value > value-weight))))

(defn get-fractions
  "Obtengo la relación valor por unidad y una secuencia que controla el número de veces que pueden ser utilizadas las fracciones para llenar el knapsack"
  [val wght]
  (take wght (repeat (double (/ val wght)))))

;; Tengo el problema que necesito devolver dos valores. Un mapa es la solución
(comment 
(defn fractions
  "Función auxiliar para calcular el decremento de la capacidad del morral"
  [capacity {:keys [weight]}]
  (if (> weight capacity)
    (- capacity 1)
    (- capacity weight)))
)

(defn fractions
  "Función auxiliar para calcular el decremento de la capacidad del morral"
  [capacity {:keys [value weight]}]
  (if (> weight capacity)
    {:capacity (- capacity capacity) :value (* (double(/ value weight)) capacity)}
    {:capacity (- capacity weight) :value value}))


(defn maxval
  "Función para determinar la combinación de mayor valor en función de una capacidad especificada. El primer argumento debe ser la capacidad y los siguientes valor y peso respectivamente."
  [& args]
  (let [capacity (first args)
        tuples (partition 2 (rest args))
        ordered-vals (order-by-value tuples)
        size (count ordered-vals)]
    (loop [cnt 0
           cp capacity
           res []]
      (cond
        (>= cnt size) (reduce + res)
        (= cp 0)      (reduce + res)        
        :else (recur (inc cnt)
                     (:capacity (fractions cp (get-in ordered-vals [cnt])))
                     (conj res (:value (fractions cp (get-in ordered-vals [cnt])))))
        ))))


;; Comentarios enriquecidos

(def test_input '(150 125 10 907 20 604 12 550 10 619 30))

(def test_particion (partition 2 [400 2 578 10 454 1 545 5 800 4]))

(def ordered_test_vals (order-by-value test_particion))

(def test_capacity 150)

(def cpct 15)
      
(comment
  Algoritmo para resolver el problema Knapsack

  Problema: Tenemos un bolso con una capacidad determinada y debemos llenarlo con la combinación más valiosa de objetos. Cada objeto tiene un valor y un peso asignado. Esta versión es llamada "fractional Knapsack problem" porque admite fraccionar los objetos y sus valores para acabar de llenar el bolso.

  1. Ordenar los elementos de modo que los de mayor valor estén de primeros. Esta sería la estrategia avara "greedy" propiamente dicha, pues queremos meter primero los de mayor valor y si es posible llenar el morral con el elemento más valioso, se hace. 

  2. Una vez ordenados, recorro la colección de principio a fin y voy restando cada elemento a la capacidad del morral.

  3. Repito la resta hasta que la capacidad del morral llegue a cero, lo que implica restar, descartar el elemento restado y actualizar la capacidad del morral.

  4. Si no he llegado a cero, y no quedan más elementos que agregar como enteros, se procede a agregar la fracción del elemento más valioso restante.
  )

