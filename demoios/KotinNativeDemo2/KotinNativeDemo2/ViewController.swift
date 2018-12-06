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
        
        let timeZoneLabel = UILabel(frame: CGRect(x: 0, y: 0, width: 300, height: 21))
        timeZoneLabel.center = CGPoint(x: 200, y: 355)
        timeZoneLabel.textAlignment = .center
        timeZoneLabel.font = timeZoneLabel.font.withSize(25)
        timeZoneLabel.text = "TimeZone goes here"
        view.addSubview(timeZoneLabel)
        
        countLabel!.text = String(counter.getCurrent())
        
        TimeZoneApi().getTimeZone(lat: 39.734, lng: -104.786, timestamp: Int64(Date().timeIntervalSinceNow), timeZoneFoundAction: {(response: TimeZoneResponse) -> KotlinUnit in
            timeZoneLabel.text = response.zoneName
            return KotlinUnit.init()
        })
        

    }
    
    @objc private func plusClicked() {
        countLabel?.text = String(counter.increment())
    }
    
    @objc private func minusClicked() {
        countLabel?.text = String(counter.decrement())
    }

}

