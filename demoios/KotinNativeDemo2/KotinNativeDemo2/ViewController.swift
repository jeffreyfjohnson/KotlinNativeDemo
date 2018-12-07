//
//  ViewController.swift
//  KotinNativeDemo2
//
//  Created by Jeff Johnson on 12/4/18.
//  Copyright © 2018 gsc. All rights reserved.
//

import UIKit
import commoncode

class ViewController: UIViewController {

    let counter = Counter(storage: PlatformStorage())
    var countLabel: UILabel? = nil
    var timeZoneLabel: UILabel? = nil
    var latTextField: UITextField? = nil
    var lngTextField: UITextField? = nil
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let label = UILabel(frame: CGRect(x: 0, y: 0, width: 300, height: 21))
        label.center = CGPoint(x: 200, y: 100)
        label.textAlignment = .center
        label.font = label.font.withSize(25)
        label.text = CommonKt.createApplicationScreenMessage()
        view.addSubview(label)
        
        countLabel = UILabel(frame: CGRect(x: 0, y: 0, width: 300, height: 21))
        countLabel?.center = CGPoint(x: 200, y: 200)
        countLabel?.textAlignment = .center
        countLabel?.font = countLabel?.font?.withSize(25)
        countLabel?.text = "0"
        view.addSubview(countLabel!)
        
        let minusButton = UIButton(frame: CGRect(x: 0, y: 0, width: 40, height: 40))
        minusButton.center = CGPoint(x: 160, y: 255)
        minusButton.backgroundColor = UIColor.blue
        minusButton.setTitleColor(UIColor.yellow, for: .normal)
        minusButton.setTitle("-", for: .normal)
        view.addSubview(minusButton)
        minusButton.addTarget(self, action: #selector(minusClicked), for: .touchUpInside)
        
        let plusButton = UIButton(frame: CGRect(x: 0, y: 0, width: 40, height: 40))
        plusButton.center = CGPoint(x: 240, y: 255)
        plusButton.backgroundColor = UIColor.blue
        plusButton.setTitleColor(UIColor.yellow, for: .normal)
        plusButton.setTitle("+", for: .normal)
        view.addSubview(plusButton)
        plusButton.addTarget(self, action: #selector(plusClicked), for: .touchUpInside)
        
        latTextField = UITextField(frame: CGRect(x: 0, y: 0, width: 100, height: 40))
        latTextField?.center = CGPoint(x: 120, y: 355)
        latTextField?.text = "39.7392"
        view.addSubview(latTextField!)
        
        lngTextField = UITextField(frame: CGRect(x: 0, y: 0, width: 100, height: 40))
        lngTextField?.center = CGPoint(x: 280, y: 355)
        lngTextField?.text = "-104.9903"
        view.addSubview(lngTextField!)
        
        let searchButton = UIButton(frame: CGRect(x: 0, y: 0, width: 70, height: 40))
        searchButton.center = CGPoint(x: 200, y: 455)
        searchButton.backgroundColor = UIColor.blue
        searchButton.setTitleColor(UIColor.yellow, for: .normal)
        searchButton.setTitle("Search", for: .normal)
        view.addSubview(searchButton)
        searchButton.addTarget(self, action: #selector(searchClicked), for: .touchUpInside)
        
        timeZoneLabel = UILabel(frame: CGRect(x: 0, y: 0, width: 300, height: 30))
        timeZoneLabel?.center = CGPoint(x: 200, y: 555)
        timeZoneLabel?.textAlignment = .center
        timeZoneLabel?.font = timeZoneLabel?.font.withSize(25)
        timeZoneLabel?.text = "TimeZone goes here"
        view.addSubview(timeZoneLabel!)
        
        countLabel!.text = String(counter.getCurrent())
        
    
    }
    
    @objc private func plusClicked() {
        countLabel?.text = String(counter.increment())
    }
    
    @objc private func minusClicked() {
        countLabel?.text = String(counter.decrement())
    }
    
    @objc private func searchClicked() {
        let lat = Double(latTextField!.text!)
        let lng = Double(lngTextField!.text!)
        let timestamp = Int64(Date().timeIntervalSinceNow)
        let positionAndTime = SwiftPositionAndTime(lat: lat!, lng: lng!, timestamp: timestamp)
        TimeZoneApi().getTimeZone(positionAndTime: positionAndTime, timeZoneFoundAction: {(response: TimeZoneResponse) -> KotlinUnit in
            self.timeZoneLabel?.text = response.zoneName
            return KotlinUnit.init()
        })
    }
}

class SwiftPositionAndTime: NSObject, PositionAndTime {
    var lat: Double
    var lng: Double
    var timestamp: Int64
    
    public init(lat: Double, lng: Double, timestamp: Int64) {
        self.lat = lat
        self.lng = lng
        self.timestamp = timestamp
    }
}
