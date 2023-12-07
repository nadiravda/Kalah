import { Component, Input } from '@angular/core';
import { FieldType } from '../models/field-type.model';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-field',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './field.component.html',
  styleUrl: './field.component.scss',
})
export class FieldComponent {
  @Input() type: FieldType = FieldType.SmallField;
  @Input() number: number = 0;
  @Input() isClickable: boolean = true;
  @Input() fieldValid: boolean = false;

  fieldTypes = FieldType;

  getBallsArray(): number[] {
    return Array(this.number).fill(0);
  }

  getCirclePosition(index: number): { top: string; left: string } {
    const spacing = 10;
    const horizontalOffset = 10;
    const startOffset = this.type === FieldType.BigField ? 40 : 20;

    const row = Math.floor(index / 4);
    const col = index % 4;

    const top = `${50 + startOffset - row * spacing}%`;
    const left = `${50 - col * spacing + horizontalOffset}%`;

    return { top, left };
  }
}
