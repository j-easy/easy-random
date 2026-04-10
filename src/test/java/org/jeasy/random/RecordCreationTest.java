/*
 * The MIT License
 *
 *   Copyright (c) 2023, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */
package org.jeasy.random;

import org.assertj.core.api.Assertions;
import org.jeasy.random.records.CityRegistryEntry;
import org.jeasy.random.records.CargoBay;
import org.jeasy.random.records.CommandConsole;
import org.jeasy.random.records.Dog;
import org.jeasy.random.records.DogOwner;
import org.jeasy.random.records.ExpeditionManifest;
import org.jeasy.random.records.ExpeditionVehicle;
import org.jeasy.random.records.IdentityCard;
import org.jeasy.random.records.LaunchBay;
import org.jeasy.random.records.ManifestReference;
import org.jeasy.random.records.MissionCommander;
import org.jeasy.random.records.NationalIdentityCard;
import org.jeasy.random.records.Person;
import org.jeasy.random.records.ResearchAirship;
import org.jeasy.random.records.ResidentProfile;
import org.jeasy.random.records.ResidentArchiveEntry;
import org.jeasy.random.records.ResidentDocument;
import org.jeasy.random.records.Rottweiler;
import org.junit.jupiter.api.Test;

public class RecordCreationTest {

    @Test
    void testRandomRecordCreation() {
        // given
        EasyRandom easyRandom = new EasyRandom();

        // when
        Person person = easyRandom.nextObject(Person.class);

        // then
        Assertions.assertThat(person).isNotNull();
        Assertions.assertThat(person.id()).isNotNull();
        Assertions.assertThat(person.name()).isNotNull();
    }

    @Test
    void testRandomRecordCreationWithRecordComponent() {
        // given
        EasyRandomParameters easyRandomParameters = new EasyRandomParameters()
                .scanClasspathForConcreteTypes(true);
        EasyRandom easyRandom = new EasyRandom(easyRandomParameters);

        // when
        DogOwner dogOwner = easyRandom.nextObject(DogOwner.class);

        // then
        Assertions.assertThat(dogOwner).isNotNull();
        Assertions.assertThat(dogOwner.ownerName()).isNotNull();
        Dog dog = dogOwner.dog();
        Assertions.assertThat(dog).isNotNull();
        Assertions.assertThat(dog).isInstanceOf(Rottweiler.class);
        Rottweiler rottweiler = (Rottweiler) dog;
        Assertions.assertThat(rottweiler.name()).isNotNull();
    }

    @Test
    void testRandomRecordCreationWithThreeLevelsOfRecordComponents() {
        // given
        EasyRandom easyRandom = new EasyRandom();

        // when
        CityRegistryEntry cityRegistryEntry = easyRandom.nextObject(CityRegistryEntry.class);

        // then
        Assertions.assertThat(cityRegistryEntry).isNotNull();
        Assertions.assertThat(cityRegistryEntry.registryId()).isNotNull();
        ResidentProfile residentProfile = cityRegistryEntry.residentProfile();
        Assertions.assertThat(residentProfile).isNotNull();
        Assertions.assertThat(residentProfile.city()).isNotNull();
        IdentityCard identityCard = residentProfile.identityCard();
        Assertions.assertThat(identityCard).isNotNull();
        Assertions.assertThat(identityCard.documentNumber()).isNotNull();
        Person person = identityCard.person();
        Assertions.assertThat(person).isNotNull();
        Assertions.assertThat(person.id()).isNotNull();
        Assertions.assertThat(person.name()).isNotNull();
    }

    @Test
    void testRandomRecordCreationWithInterfaceRecordComponent() {
        // given
        EasyRandomParameters easyRandomParameters = new EasyRandomParameters()
                .scanClasspathForConcreteTypes(true);
        EasyRandom easyRandom = new EasyRandom(easyRandomParameters);

        // when
        ResidentArchiveEntry residentArchiveEntry = easyRandom.nextObject(ResidentArchiveEntry.class);

        // then
        Assertions.assertThat(residentArchiveEntry).isNotNull();
        Assertions.assertThat(residentArchiveEntry.archiveId()).isNotNull();
        ResidentDocument residentDocument = residentArchiveEntry.residentDocument();
        Assertions.assertThat(residentDocument).isNotNull();
        Assertions.assertThat(residentDocument).isInstanceOf(NationalIdentityCard.class);
        NationalIdentityCard nationalIdentityCard = (NationalIdentityCard) residentDocument;
        Assertions.assertThat(nationalIdentityCard.referenceNumber()).isNotNull();
        Person person = nationalIdentityCard.person();
        Assertions.assertThat(person).isNotNull();
        Assertions.assertThat(person.id()).isNotNull();
        Assertions.assertThat(person.name()).isNotNull();
    }

    @Test
    void testRandomRecordCreationWithMixedHierarchy() {
        // given
        EasyRandomParameters easyRandomParameters = new EasyRandomParameters()
                .scanClasspathForConcreteTypes(true);
        EasyRandom easyRandom = new EasyRandom(easyRandomParameters);

        // when
        ExpeditionManifest expeditionManifest = easyRandom.nextObject(ExpeditionManifest.class);

        // then
        Assertions.assertThat(expeditionManifest).isNotNull();
        ManifestReference manifestReference = expeditionManifest.manifestReference();
        Assertions.assertThat(manifestReference).isNotNull();
        Assertions.assertThat(manifestReference.manifestCode()).isNotNull();
        Assertions.assertThat(manifestReference.departureZone()).isNotNull();
        Assertions.assertThat(manifestReference.priorityLabel()).isNotNull();
        ExpeditionVehicle expeditionVehicle = expeditionManifest.expeditionVehicle();
        Assertions.assertThat(expeditionVehicle).isNotNull();
        Assertions.assertThat(expeditionVehicle).isInstanceOf(ResearchAirship.class);
        ResearchAirship researchAirship = (ResearchAirship) expeditionVehicle;
        Assertions.assertThat(researchAirship.vehicleName()).isNotNull();
        LaunchBay launchBay = researchAirship.launchBay();
        Assertions.assertThat(launchBay).isNotNull();
        Assertions.assertThat(launchBay.bayCode()).isNotNull();
        Assertions.assertThat(launchBay.weatherWindow()).isNotNull();
        Assertions.assertThat(launchBay.accessBadge()).isNotNull();
        CargoBay cargoBay = researchAirship.cargoBay();
        Assertions.assertThat(cargoBay).isNotNull();
        Assertions.assertThat(cargoBay.cargoCode()).isNotNull();
        Assertions.assertThat(cargoBay.supplyCrate()).isNotNull();
        Assertions.assertThat(cargoBay.cargoInspector()).isNotNull();
        MissionCommander missionCommander = expeditionManifest.missionCommander();
        Assertions.assertThat(missionCommander).isNotNull();
        Assertions.assertThat(missionCommander.getCommanderName()).isNotNull();
        Assertions.assertThat(missionCommander.getRank()).isNotNull();
        CommandConsole commandConsole = missionCommander.getCommandConsole();
        Assertions.assertThat(commandConsole).isNotNull();
        Assertions.assertThat(commandConsole.getConsoleId()).isNotNull();
        Assertions.assertThat(commandConsole.getWeatherWindow()).isNotNull();
        Assertions.assertThat(commandConsole.getAccessBadge()).isNotNull();
    }

}
