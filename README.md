# FirebaseAuth-Kotlin

App móvil con login básico basado en firebase.

Patrón de diseño: MVVM

- La capa del repositorio está basada en **Firebase**, permitiendo iniciar sesión o registrarse a través de correo y clave ó a través de Gmail. 

- Los ViewModel se encargan de consultar los repositorios y mantiene preparada la data en observables para ser observadas por las vistas, provee los métodos necesarios para que las vistas envíen eventos a los modelos. 

- En las vistas se captan los datos para el inicio de sesión o registro y observan las información enviada desde los repositorios.
