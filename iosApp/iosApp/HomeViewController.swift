import UIKit
import app
import CoreLocation

class ViewController: UIViewController, CLLocationManagerDelegate, UICollectionViewDataSource, UICollectionViewDelegate, HomeView {
    
    @IBOutlet weak var forecastList: UICollectionView!
    
    
    private lazy var presenter = HomePresenter(
        repository: CommonRepository(local: CommonLocalDataSource(),
        remote: CommonRemoteDataSource()),
        error: ErrorHandler(),
        executor: Executor(),
        view: self
    )
    
    var locationManager: CLLocationManager!
    
    var forecast: [Forecast] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        locationManager = CLLocationManager()
        presenter.attach()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    
    func checkLocationPermission() {
        locationManager.delegate = self
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.requestAlwaysAuthorization()
    }
    
    func fetchLocation() {
        if CLLocationManager.locationServicesEnabled() {
            locationManager.startUpdatingLocation()
        }
    }
    
    func showForecast(weather: [Forecast]) {
        self.forecast = weather
        self.forecastList.reloadData()
        
        print(forecast.count)
    }
    
    func hideProgress() {
        // TODO
    }
    
    func showProgress() {
        // TODO
    }
    
    func showRetry(error: String, f: @escaping () -> Void) {
        // TODO
    }
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        let userLocation:CLLocation = locations[0] as CLLocation
        presenter.onLocationFound(latitude: userLocation.coordinate.latitude, longitude: userLocation.coordinate.longitude)
    }
    
    private func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        print("Error \(error)")
    }

    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        print(status)
        switch (status) {
            case (.authorizedAlways):
                self.presenter.onPermissionGranted()
            default:
                print("Not auth")
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return forecast.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "forecast", for: indexPath) as! ForecastViewCell
        
        let forecast = self.forecast[indexPath.row]
        
        let url = URL(string: "https://openweathermap.org/img/wn/" + forecast.weather[0].icon + "@2x.png")
        let data = try? Data(contentsOf: url!)
        
        cell.icon.image = UIImage(data: data!)
        cell.icon.contentMode = .scaleAspectFit
        cell.icon.clipsToBounds = true
        
        cell.date.text = forecast.dt_txt
        
        cell.temp.text = String(forecast.main.temp) + "ºC"
        
        cell.status.text = forecast.weather[0].main
        
        return cell
    }

}
